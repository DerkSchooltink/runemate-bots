package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.script.Execution

class LootAnagogicOrt : ExtendedTask<Citadel>() {

    private var ort: GroundItem? = null

    override fun validate(): Boolean {
        ort = GroundItems.newQuery().names("Anagogic ort").results().nearest()
        return ort != null
    }

    override fun execute() {
        logger.info("Looting anagogic ort...")
        ort?.let {
            if (it.interact("Take")) {
                Execution.delayUntil({ !it.isValid }, 600, 1200)
            }
        }
    }
}
