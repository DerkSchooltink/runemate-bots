package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.util.calculations.Distance

class OpenBank : ExtendedTask<CatacombsSlayer>() {

    private var bank: LocatableEntity? = null

    override fun validate(): Boolean {
        bank = Banks.newQuery().results().nearest()
        return bank?.isVisible == true && Distance.to(bank) < 10
                && !Bank.isOpen()
                && (Inventory.isFull() || !Inventory.containsAnyOf(bot.botSettings.food.toString()))
    }

    override fun execute() {
        logger.info("Opening bank...")
        Bank.open(bank)
    }
}
