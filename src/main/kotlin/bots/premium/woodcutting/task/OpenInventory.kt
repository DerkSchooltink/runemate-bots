package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop

class OpenInventory : ExtendedTask<WoodCutting>() {

    override fun validate() = !InterfaceWindows.getInventory().isOpen && !Bank.isOpen() && !DepositBox.isOpen() && !Shop.isOpen()

    override fun execute() {
        logger.info("Opening inventory...")
        InterfaceWindows.getInventory().open()
    }
}
