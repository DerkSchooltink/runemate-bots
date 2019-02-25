package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToBank : ExtendedTask<Ascension>() {

    override fun validate(): Boolean {
        val bank = Banks.newQuery().results().nearest()
        return !hasEnoughItems() && (bank == null || !bank.isVisible || Distance.to(bank) > 5)
    }

    override fun execute() {
        logger.info("Walking to bank...")
        PathBuilder.buildTo(Landmark.BANK)?.step()
    }

    private fun hasEnoughPrayerItems() = !bot.ascensionSettings.isUsingPrayer || Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
    private fun hasEnoughFood() = !bot.ascensionSettings.isUsingFood || Inventory.containsAnyOf(bot.ascensionSettings.food.toString())
    private fun hasEnoughMagicNotepaper() = !bot.ascensionSettings.isUsingMagicNotepaper || Inventory.contains("Magic notepaper")
    private fun hasEnoughItems() = hasEnoughFood() && hasEnoughMagicNotepaper() && hasEnoughPrayerItems()
}
