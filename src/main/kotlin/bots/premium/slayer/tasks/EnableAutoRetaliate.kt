package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar

class EnableAutoRetaliate : ExtendedTask<Slayer>() {

    override fun validate() = !ActionBar.isAutoRetaliating() && !Bank.isOpen()

    override fun execute() {
        logger.info("Enabling auto-retaliate...")
        ActionBar.toggleAutoRetaliation()
    }
}
