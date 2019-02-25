package bots.free.summoning.tasks

import bots.free.summoning.Summoning
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.util.calculations.Distance
import java.util.regex.Pattern

class WithdrawPreset : ExtendedTask<Summoning>() {

    private var bank: LocatableEntity? = null

    override fun validate(): Boolean {
        bank = Banks.newQuery().results().nearest()
        return (Distance.to(bank) < 5 || bank?.isVisible == true) && (Inventory.contains(Pattern.compile(".*pouch.*")) || Inventory.isEmpty())
    }

    override fun execute() {
        if (DepositBox.isOpen()) DepositBox.close()
        if (!Bank.isOpen()) Bank.open(bank) else Bank.loadPreset(bot.botSettings.preset)
    }
}