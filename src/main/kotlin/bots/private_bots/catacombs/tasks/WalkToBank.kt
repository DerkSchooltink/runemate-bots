package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.navigation.BankLocation
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory

class WalkToBank : ExtendedTask<CatacombsSlayer>() {

    override fun validate() = !Bank.isOpen() && (Inventory.isFull() || !Inventory.containsAnyOf(bot.botSettings.food.toString()))

    override fun execute() {
        logger.info("Walking to bank...")
        PathBuilder.buildTo(BankLocation.KOUREND_ARCEUS)?.step(false)
    }
}
