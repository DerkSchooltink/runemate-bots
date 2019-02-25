package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names

class DrinkAntifire : ExtendedTask<Slayer>() {

    override fun validate() = hasDragonTask()
            && noAntifireActive()
            && monstersNearby()
            && Inventory.containsAnyOf(bot.slayerSettings.antifirePotion?.pattern)

    override fun execute() {
        if (drink()) logger.info("Drinking antifire potion...")
    }

    private fun drink() = Inventory.newQuery().names(bot.slayerSettings.antifirePotion?.pattern).results().first().let {
        it?.definition?.let { definition -> Inventory.containsAnyOf(definition.name) && definition.inventoryActions?.contains("Drink") == true } == true && it.interact("Drink")
    }

    private fun hasDragonTask() = bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.DRAGON
    private fun monstersNearby() = !Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isEmpty()
    private fun noAntifireActive() = bot.slayerSettings.antifirePotion?.getVarbit()?.value == 0
}
