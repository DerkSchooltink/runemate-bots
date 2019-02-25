package bots.free.mining.sandstone.task

import bots.free.mining.sandstone.Sandstone
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class CatchButterfly : ExtendedTask<Sandstone>() {

    private var butterfly: Npc? = null

    override fun validate(): Boolean {
        butterfly = Npcs.newQuery().names("Guthixian butterfly").results().nearest()
        return !bot.botSettings.isCatchingButterflies && butterfly != null
    }

    override fun execute() {
        butterfly?.let {
            if (!it.isVisible) BresenhamPath.buildTo(it)?.step()
            if (it.turnAndInteract("Catch")) {
                logger.info("Catching butterfly...")
                Execution.delayUntil({ !it.isValid }, 1200, 1300)
            }
        }
    }
}
