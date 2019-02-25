package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Attack : ExtendedTask<Ascension>() {

    private var monster: Npc? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()

        if (player == null) {
            return false
        }

        monster = Npcs.newQuery().names("Rorarius").withinRadius(20).results().nearest()

        return monster != null
                && Npcs.newQuery().names("Rorarius").targeting(player).within(Area.Circular(player?.position, 20.0)).results().isEmpty()
                && (!bot.ascensionSettings.isUsingMagicNotepaper || Inventory.newQuery().unnoted().names(*bot.ascensionSettings.keys.toTypedArray()).results().isEmpty())
                && hasEnoughItems()
                && hasNoTargets()
                && noItemsToDisassemble()
    }

    override fun execute() {
        if (monster.turnAndInteract("Attack")) {
            logger.info("Attacking rorarii...")
            Execution.delayUntil({ player?.target == null }, 800, 1200)
        }
    }

    private fun hasNoTargets() = (monster?.target == null || monster?.target == player) && (player?.target == null || Npcs.newQuery().targeting(player).results().isEmpty())
    private fun noItemsToDisassemble() = !bot.ascensionSettings.isUsingInvention || Inventory.containsAnyOf("Runite limbs", "Adamant longsword", "Mithril platebody")
    private fun hasEnoughPrayerSupplies() = !bot.ascensionSettings.isUsingPrayer || Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
    private fun hasEnoughMagicNotepaper() = !bot.ascensionSettings.isUsingMagicNotepaper || Inventory.containsAnyOf("Magic notepaper")
    private fun hasEnoughFood() = !bot.ascensionSettings.isUsingFood || Inventory.containsAnyOf(bot.ascensionSettings.food.toString())
    private fun hasEnoughItems() = hasEnoughFood() && hasEnoughMagicNotepaper() && hasEnoughPrayerSupplies()
}
