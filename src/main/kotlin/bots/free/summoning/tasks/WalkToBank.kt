package bots.free.summoning.tasks

import bots.free.summoning.Summoning
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToBank : ExtendedTask<Summoning>() {

    private var bank: LocatableEntity? = null

    override fun validate(): Boolean {
        bank = Banks.newQuery().results().nearest()
        return (Distance.to(bank) >= 5 || bank?.isVisible == false) && Inventory.contains(bot.botSettings.pouchName)
    }

    override fun execute() {
        PathBuilder.buildTo(bot.botSettings.obelisk.bankLocation)?.step()
    }
}