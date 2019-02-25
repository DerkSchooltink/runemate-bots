package bots.free.runespan.tasks

import bots.free.runespan.Runespan
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class GrabKnowledge : ExtendedTask<Runespan>() {

    private var knowledge: Npc? = null

    override fun validate(): Boolean {
        knowledge = Npcs.newQuery().names("Manifested knowledge").actions("Siphon").results().nearest()
        return knowledge != null
    }

    override fun execute() {
        knowledge?.let {
            if (it.turnAndInteract("Siphon")) {
                logger.info("Grabbing manifested knowledge...")
                Execution.delayUntil({ !it.isValid }, 1200, 1800)
            }
        }
    }
}
