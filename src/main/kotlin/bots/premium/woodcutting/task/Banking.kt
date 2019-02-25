package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import quantum.api.core.ExtendedTask

class Banking : ExtendedTask<WoodCutting>() {

    override fun validate() = bankIsOpen()
            && (Inventory.isFull() || (Bank.isOpen() && bot.settings.run { Inventory.containsAnyExcept(axePattern, urnPattern, jujuPattern) }))
            && !bot.settings.isBurningLogs.value

    override fun execute() {
        if (Shop.isOpen()) {
            if (Shop.sell(bot.settings.tree.value.logs, 0)) logger.info("Selling items to shop...")
        } else {
            bot.settings.run {
                if (DepositBox.isOpen()) {
                    if (!Inventory.containsAnyOf(axePattern, urnPattern, jujuPattern)) {
                        if (DepositBox.depositInventory()) logger.info("Banking using depositbox...")
                    } else {
                        if (DepositBox.depositAllExcept(axePattern, urnPattern, jujuPattern)) logger.info("Banking using depositbox...")
                    }
                } else if (Bank.isOpen()) {
                    if (!Inventory.containsAnyOf(axePattern, urnPattern, jujuPattern)) {
                        if (Bank.depositInventory()) logger.info("Banking using bank...")
                    } else {
                        if (Bank.depositAllExcept(axePattern, urnPattern, jujuPattern)) logger.info("Banking using bank...")
                    }
                }
            }
        }
    }

    private fun bankIsOpen() = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
}
