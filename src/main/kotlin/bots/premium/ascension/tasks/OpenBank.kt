package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks

class OpenBank : ExtendedTask<Ascension>() {

    override fun validate(): Boolean {
        val bank = Banks.newQuery().results().nearest()
        return (bank != null
                && bank.isVisible
                && !Bank.isOpen()
                && !hasEnoughItems())
    }

    override fun execute() {
        logger.info("Opening bank...")
        Bank.open()
    }

    private fun hasEnoughPrayerItems() = !bot.ascensionSettings.isUsingPrayer || Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
    private fun hasEnoughFood() = !bot.ascensionSettings.isUsingFood || Inventory.containsAnyOf(bot.ascensionSettings.food.toString())
    private fun hasEnoughMagicNotepaper() = !bot.ascensionSettings.isUsingMagicNotepaper || Inventory.contains("Magic notepaper")
    private fun hasEnoughItems() = hasEnoughFood() && hasEnoughMagicNotepaper() && hasEnoughPrayerItems()
}
