package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Players

class Destroy : ExtendedTask<WoodCutting>() {

    override fun validate() = Inventory.containsAnyOf("Special teak log", "Special mahogany log")
            && InterfaceWindows.getInventory().isOpen
            && bot.settings.isPowerchopping.value
            && playerIsIdle() == true
            && Inventory.getSelectedItem() == null

    override fun execute() {
        logger.info("Destroying items...")

        val yesButton = Interfaces.newQuery().containers(1183).texts("Yes").types(InterfaceComponent.Type.LABEL).results().first()
        val allButton = Interfaces.newQuery().containers(1183).texts("All").types(InterfaceComponent.Type.LABEL).results().first()

        allButton?.click() ?: yesButton?.click()

        if (yesButton == null && allButton == null) {
            Inventory.newQuery().names("Special teak log", "Special mahogany log").actions("Destroy").results().first()?.interact("Destroy")
        }
    }

    private fun playerIsIdle(): Boolean? = Players.getLocal()?.run { animationId == -1 && !isMoving }
}

