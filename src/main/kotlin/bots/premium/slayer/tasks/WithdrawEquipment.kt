package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.game.combat.CombatStyle
import quantum.api.game.turnAndInteract
import java.util.regex.Pattern

class WithdrawEquipment : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.task != null
            && bot.slayerSettings.task?.amount != 0
            && Npcs.newQuery().names(bot.slayerSettings.task?.names).targeting(Players.getLocal()).results().isEmpty()
            && Banks.newQuery().results().nearest()?.let { Distance.to(it) < 5 || it.isVisible } == true
            && (hasNoFoodLeft() || noAntifireLeft() || noInventorySpaceLeft() || !isWearingRightEquipment() || !hasCorrectCombatStyle())

    override fun execute() {
        Banks.newQuery().results().nearest()?.let {
            if (!Bank.isOpen()) {
                logger.info("Opening bank...")
                it.turnAndInteract("Bank")
            } else {
                bot.slayerSettings.task?.requirements?.styleSlayer?.presetNumber?.let { preset ->
                    if (Bank.loadPreset(preset)) {
                        logger.info("Loading preset $it...")
                        Execution.delayUntil({ !Bank.isOpen() }, 600, 1200)
                    }
                }
            }
        }
    }

    private fun noAntifireLeft() =
        bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.DRAGON && !Inventory.containsAnyOf(bot.slayerSettings.antifirePotion?.pattern)

    private fun hasNoFoodLeft() = Inventory.newQuery().actions("Eat").results().isEmpty()
    private fun isWearingRightEquipment() = bot.slayerSettings.task?.monster?.requirements?.hasEquipment() == true
    private fun noInventorySpaceLeft() =
        Inventory.getEmptySlots() <= 1 || Inventory.newQuery().names(Pattern.compile("(.* teleport)")).results().isEmpty()

    private fun hasCorrectCombatStyle() =
        bot.slayerSettings.task?.requirements?.styleSlayer?.combatStyle.let { it == CombatStyle.getCombatStyle() || it == CombatStyle.UNKNOWN }
}