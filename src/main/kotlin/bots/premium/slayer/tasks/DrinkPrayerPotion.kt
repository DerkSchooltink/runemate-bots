package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class DrinkPrayerPotion : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.useQuickPrayer
            && Powers.Prayer.getPoints() < Powers.Prayer.getMaximumPoints() / 7
            && Inventory.containsAnyOf(bot.slayerSettings.prayerPotion?.pattern)
            && !Bank.isOpen()
            && monstersNearby()

    override fun execute() {
        if (drink()) logger.info("Drinking prayer potion...")
    }

    private fun drink() = Inventory.newQuery().names(bot.slayerSettings.prayerPotion?.pattern).results().first().let { potion ->
        potion?.definition?.let { Inventory.containsAnyOf(it.name) && it.inventoryActions?.contains("Drink") == true } == true && potion.interact("Drink")
    }

    private fun monstersNearby() = !Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isEmpty()
}
