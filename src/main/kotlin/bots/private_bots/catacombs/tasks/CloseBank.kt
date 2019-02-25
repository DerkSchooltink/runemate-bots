package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class CloseBank : ExtendedTask<CatacombsSlayer>() {

    override fun validate(): Boolean {
        return Bank.isOpen()
                && Inventory.containsAnyOf(bot.botSettings.food.toString())
                && hasEnoughAttackPotions()
                && hasEnoughStrengthPotions()
    }

    override fun execute() {
        logger.info("Closing bank...")
        Bank.close()
    }

    private fun hasEnoughAttackPotions() = bot.botSettings.attackPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.attackPotion?.pattern)
    private fun hasEnoughStrengthPotions() = bot.botSettings.strengthPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.strengthPotion?.pattern)
}
