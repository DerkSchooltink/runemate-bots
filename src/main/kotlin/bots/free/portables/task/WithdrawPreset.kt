package bots.free.portables.task

import bots.free.portables.Portables
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.rs3.local.hud.interfaces.MakeXInterface
import com.runemate.game.api.script.Execution
import quantum.api.core.ExtendedTask

class WithdrawPreset : ExtendedTask<Portables>() {

    private var bank: LocatableEntity? = null

    override fun validate(): Boolean {
        bank = Banks.newQuery().results().nearest()
        return (!Inventory.contains(bot.portableSettings.baseItem)
                || (bot.portableSettings.secondBaseItem.isNotBlank() && !Inventory.contains(bot.portableSettings.secondBaseItem)))
                && bank != null
    }

    override fun execute() {
        if (MakeXInterface.isOpen()) MakeXInterface.close()
        when {
            !Bank.isOpen() -> bank?.let {
                if (!it.isVisible) Camera.concurrentlyTurnTo(it)
                if (Bank.open(it)) Execution.delayUntil(Bank::isOpen, 600, 1200)
            }
            else -> Bank.loadPreset(1)
        }
    }
}