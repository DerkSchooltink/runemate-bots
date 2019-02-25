package bots.free.summoning.tasks

import bots.free.summoning.Summoning
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects

class WalkToObelisk : ExtendedTask<Summoning>() {

    override fun validate() = !Bank.isOpen()
            && Inventory.containsAllOf(*bot.botSettings.ingredients)
            && GameObjects.newQuery().names("Obelisk").withinRadius(5).results().isEmpty()

    override fun execute() {
        PathBuilder.buildTo(bot.botSettings.obelisk.obeliskLocation)?.step()
    }
}