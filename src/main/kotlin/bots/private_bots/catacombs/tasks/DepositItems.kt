package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class DepositItems : ExtendedTask<CatacombsSlayer>() {

    override fun validate() = !Inventory.isEmpty()
            && Bank.isOpen()
            && (Inventory.isFull() || (!Inventory.containsAnyOf(bot.botSettings.food.toString()) && !Inventory.containsAnyOf("Dark totem base", "Dark totem top", "Dark totem middle")))

    override fun execute() {
        logger.info("Depositting inventory...")
        Bank.depositAllExcept("Dark totem base", "Dark totem top", "Dark totem middle")
    }
}
