package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Loot : ExtendedTask<Muspah>() {

    override fun validate() = !Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
            && !Inventory.isFull()
            && !GroundItems.newQuery().names(*bot.settings.allLoot).results().isEmpty()

    override fun execute() {
        GroundItems.newQuery().names(*bot.settings.allLoot).results().nearest()?.let {
            if (it.turnAndInteract("Take")) {
                Execution.delayUntil({ !it.isValid }, { Players.getLocal()?.isMoving }, 600, 1200)
            }
        }
    }
}
