package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import quantum.api.core.ExtendedTask
import quantum.api.game.interact
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.script.Execution

class Loot : ExtendedTask<Slayer>() {

    private var lootItem: GroundItem? = null

    override fun validate(): Boolean {
        lootItem = bot.lootUpdater.getLootOrNull()
        return Health.getCurrentPercent() > 50
                && inventoryCanTakeLoot(lootItem)
                && antifireActive()
                && eatFoodToMakeRoom()
                && InterfaceWindows.getInventory().open()
    }

    override fun execute() {
        if (lootItem.turnAndInteract("Take")) {
            logger.info("Looting...")
            Execution.delayUntil({ lootItem?.isValid == false }, 1500, 2500)
        }
    }

    private fun antifireActive() = bot.slayerSettings.task?.requirements?.styleSlayer != SlayerCombatStyle.DRAGON ||
            bot.slayerSettings.antifirePotion?.isDosed() == true

    private fun inventoryCanTakeLoot(item: GroundItem?): Boolean {
        val definition = item?.definition ?: return false
        return !Inventory.isFull() || (definition.stacks()) && Inventory.contains(definition.name)
    }

    private fun eatFoodToMakeRoom() = Inventory.newQuery().actions("Eat").results().let {
        if (it.isNotEmpty() && Inventory.getEmptySlots() <= 2) it.random().interact("Eat") else true
    }
}
