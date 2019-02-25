package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class WithdrawItems : ExtendedTask<CatacombsSlayer>() {

    override fun validate() = Bank.isOpen()
                && (!Inventory.containsAnyOf(bot.botSettings.food.toString())
                || !hasEnoughAttackPotions()
                || !hasEnoughStrengthPotions())
    
    override fun execute() {
        logger.info("Withdrawing items...")
        if (!Inventory.containsAnyOf(bot.botSettings.food.toString())) {
            Bank.withdraw(bot.botSettings.food.toString(), bot.botSettings.foodAmount)
        }
        if (!Inventory.containsAnyOf(bot.botSettings.strengthPotion?.pattern)) {
            Bank.withdraw(bot.botSettings.strengthPotion?.pattern, bot.botSettings.strengthPotionAmount)
        }
        if (!Inventory.containsAnyOf(bot.botSettings.attackPotion?.pattern)) {
            Bank.withdraw(bot.botSettings.attackPotion?.pattern, bot.botSettings.attackPotionAmount)
        }
    }

    private fun hasEnoughAttackPotions() = bot.botSettings.attackPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.attackPotion?.pattern)
    private fun hasEnoughStrengthPotions() = bot.botSettings.strengthPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.strengthPotion?.pattern)
}
