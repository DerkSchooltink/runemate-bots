package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop

class CloseBank : ExtendedTask<WoodCutting>() {

    override fun validate() = bankIsOpen() && hasNoItemsToBank()

    override fun execute() {
        logger.info("Closing bank...")
        when {
            Bank.isOpen() -> Bank.close()
            DepositBox.isOpen() -> DepositBox.close()
            Shop.isOpen() -> Shop.close()
        }
    }

    private fun bankIsOpen() = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
    private fun hasNoItemsToBank() = Inventory.isEmpty() || bot.settings.run { Inventory.containsOnly(jujuPattern, urnPattern, axePattern) }
}
