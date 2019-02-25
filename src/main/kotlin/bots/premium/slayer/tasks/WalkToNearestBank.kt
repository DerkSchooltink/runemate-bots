package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import quantum.api.core.ExtendedTask
import quantum.api.game.combat.CombatStyle
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.location.navigation.Traversal
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class WalkToNearestBank : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.task != null
            && Banks.newQuery().results().nearest().let { it == null || !it.isVisible || Distance.to(it) > 5 }
            && (taskIsDone() || hasNoFoodLeft() || noAntifireLeft() || noInventorySpaceLeft() || !hasCorrectCombatStyle())

    override fun execute() {
        logger.info("Walking to nearest bank...")
        Inventory.newQuery().names(Pattern.compile("(.* teleport)")).results().first().let {
            if (it != null && !Npcs.newQuery().targeting(Players.getLocal()).results().isEmpty()) {
                if (it.click()) Execution.delayUntil({ !it.isValid }, 5200, 5600) else it.click()
            } else Traversal.getDefaultWeb().pathBuilder.buildTo(Landmark.BANK)?.step()
        }
    }

    private fun noAntifireLeft() = bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.DRAGON && Inventory.newQuery().names(bot.slayerSettings.antifirePotion?.pattern).results().isEmpty()
    private fun noInventorySpaceLeft() = Inventory.isFull() || Inventory.newQuery().names(Pattern.compile("(.* teleport)")).results().isEmpty()
    private fun hasNoFoodLeft() = Inventory.newQuery().actions("Eat").results().isEmpty() && Health.getCurrentPercent() < 40
    private fun hasCorrectCombatStyle() = bot.slayerSettings.task?.requirements?.styleSlayer?.combatStyle.let { it == CombatStyle.getCombatStyle() || it == CombatStyle.UNKNOWN }
    private fun taskIsDone() = bot.slayerSettings.task?.amount == 0
}
