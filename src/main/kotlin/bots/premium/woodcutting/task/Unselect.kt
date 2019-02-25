package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.game.Interaction
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.script.Execution

class Unselect : ExtendedTask<WoodCutting>() {

    override fun validate() = InterfaceWindows.getInventory().isOpen
            && Inventory.getSelectedItem() != null
            && (!bot.settings.isBurningLogs.value && Inventory.isFull())

    override fun execute() {
        logger.info("Deselecting log...")
        if (Interaction.deselectItem()) Execution.delayUntil({ Inventory.getSelectedItem() == null }, 1500, 2000)
    }
}
