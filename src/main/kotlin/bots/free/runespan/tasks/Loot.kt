package bots.free.runespan.tasks

import bots.free.runespan.Runespan
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.script.Execution

class Loot : ExtendedTask<Runespan>() {

    private var loot: GroundItem? = null

    override fun validate(): Boolean {
        loot = GroundItems.newQuery().names("Anagogic ort").reachable().results().nearest()
        return loot != null
    }

    override fun execute() {
        loot?.let {
            if (it.interact("Take")) {
                logger.info("Looting...")
                Execution.delayUntil({ !it.isValid }, 1200, 1800)
            }
        }
    }
}
