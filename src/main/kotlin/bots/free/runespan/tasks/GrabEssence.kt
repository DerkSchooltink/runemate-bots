package bots.free.runespan.tasks

import bots.free.runespan.Runespan
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class GrabEssence : ExtendedTask<Runespan>() {

    private var essenceSource: Npc? = null

    override fun validate(): Boolean {
        essenceSource = Npcs.newQuery().names("Floating essence").reachable().actions("Collect").results().nearest()
        return !Inventory.containsAnyOf("Rune essence") && essenceSource != null
    }

    override fun execute() {
        logger.info("Grabbing new essences...")
        if (essenceSource.turnAndInteract("Collect")) {
            Execution.delayUntil({ Inventory.containsAnyOf("Rune essence") }, 1200, 1800)
        }
    }
}
