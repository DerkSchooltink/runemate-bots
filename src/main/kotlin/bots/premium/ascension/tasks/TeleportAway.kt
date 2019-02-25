package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class TeleportAway : ExtendedTask<Ascension>() {

    private val teleportItemPattern = Pattern.compile("(.* teleport)")
    private var teleport: SpriteItem? = null

    override fun validate(): Boolean {
        val bank = Banks.newQuery().results().nearest()
        teleport = Inventory.newQuery().names(teleportItemPattern).results().first()

        return teleport != null && !hasEnoughItems() && bank == null && !Npcs.newQuery().names("Rorarius").results().isEmpty()
    }

    override fun execute() {
        logger.info("Walking to bank...")

        if (teleport != null && teleport?.click() == true) {
            Execution.delayUntil({ teleport?.isValid == false }, 1200, 1500)
        }
    }

    private fun hasEnoughPrayerItems() = !bot.ascensionSettings.isUsingPrayer || Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
    private fun hasEnoughFood() = !bot.ascensionSettings.isUsingFood || Inventory.containsAnyOf(bot.ascensionSettings.food.toString())
    private fun hasEnoughMagicNotepaper() = !bot.ascensionSettings.isUsingMagicNotepaper || Inventory.contains("Magic notepaper")
    private fun hasEnoughItems() = hasEnoughFood() && hasEnoughMagicNotepaper() && hasEnoughPrayerItems()
}
