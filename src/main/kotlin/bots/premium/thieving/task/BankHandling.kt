package bots.premium.thieving.task

import bots.premium.thieving.ElvesThieving
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.util.calculations.Distance

class BankHandling : ExtendedTask<ElvesThieving>() {

    override fun validate() = Inventory.isFull()

    override fun execute() {
        if (Bank.isOpen()) {
            if (Bank.depositAllExcept(*bot.settings.runes.toTypedArray())) {
                Bank.close()
            }
        } else {
            Banks.newQuery().results().nearest().let {
                if (it == null) {
                    PathBuilder.buildTo(Landmark.BANK)?.step()
                } else {
                    if (it.isVisible || Distance.to(it) < 5) {
                        Bank.open(it)
                    } else {
                        PathBuilder.buildTo(it)?.step()
                    }
                }
            }
        }
    }
}