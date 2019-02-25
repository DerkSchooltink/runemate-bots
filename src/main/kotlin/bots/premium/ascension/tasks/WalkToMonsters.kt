package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToMonsters : ExtendedTask<Ascension>() {

    private val areaCoordinate = Coordinate(1110, 596, 1)
    private val isTooFarAwayFromMonsters: Boolean = Npcs.newQuery().names("Rorarius").results().isEmpty() || Distance.to(areaCoordinate) > 20

    override fun validate() = hasEnoughItems() && isTooFarAwayFromMonsters

    override fun execute() {
        logger.info("Walking to monsters...")
        PathBuilder.buildTo(areaCoordinate)?.step()
    }

    private fun hasEnoughPrayerItems() = !bot.ascensionSettings.isUsingPrayer || Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
    private fun hasEnoughFood() = !bot.ascensionSettings.isUsingFood || Inventory.containsAnyOf(bot.ascensionSettings.food.toString())
    private fun hasEnoughMagicNotepaper() = !bot.ascensionSettings.isUsingMagicNotepaper || Inventory.contains("Magic notepaper")
    private fun hasEnoughItems() = hasEnoughFood() && hasEnoughMagicNotepaper() && hasEnoughPrayerItems()
}
