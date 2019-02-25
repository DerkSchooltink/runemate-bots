package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers
import com.runemate.game.api.script.Execution

class DrinkPrayerPotion : ExtendedTask<Ascension>() {

    private var prayerPoints: Int = 0

    override fun validate(): Boolean {
        prayerPoints = Powers.Prayer.getPoints()
        return (bot.ascensionSettings.isUsingPrayer
                && prayerPoints < Powers.Prayer.getMaximumPoints() / 7
                && Inventory.containsAnyOf(bot.ascensionSettings.prayerPotion?.pattern)
                && !Bank.isOpen()
                && !Npcs.newQuery().names("Rorarius").results().isEmpty())
    }

    override fun execute() {
        val pot = Inventory.newQuery().names(bot.ascensionSettings.prayerPotion?.pattern).results().first()

        if (pot != null && Inventory.containsAnyOf(pot.definition?.name) && pot.definition?.inventoryActions?.contains("Drink") == true && pot.interact("Drink")) {
            logger.info("Drinking prayer potion...")
            Execution.delayUntil({ prayerPoints < Powers.Prayer.getPoints() }, 300, 600)
        }
    }
}
