package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import quantum.api.game.Interaction
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class DrinkPrayerPotion : ExtendedTask<Muspah>() {

    override fun validate() = Powers.Prayer.getPoints() < Powers.Prayer.getMaximumPoints() / 7
            && Inventory.containsAnyOf(bot.settings.prayerPotion?.pattern)
            && !Bank.isOpen()
            && monstersNearby()

    override fun execute() {
        if (Inventory.getSelectedItem() != null) Interaction.deselectItem()
        if (drink()) logger.info("Drinking prayer potion...")
    }

    private fun drink() = Inventory.newQuery().names(bot.settings.prayerPotion?.pattern).results().first().let { potion ->
        potion?.definition?.let { Inventory.containsAnyOf(it.name) && it.inventoryActions?.contains("Drink") == true } == true && potion.interact("Drink")
    }

    private fun monstersNearby() = !Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
}
