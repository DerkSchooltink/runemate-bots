package bots.free.mining.sandstone.task

import bots.free.mining.sandstone.Sandstone
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Mine : ExtendedTask<Sandstone>() {

    override fun validate() = !Inventory.isFull() && Players.getLocal()?.animationId == -1 && Npcs.newQuery().names("Guthixian butterfly").results().isEmpty()

    override fun execute() {
        GameObjects.newQuery().names("Mineral deposit").actions("Mine").results().nearest()?.let {
            if (Players.getLocal()?.animationId == -1 && it.turnAndInteract("Mine")) {
                logger.info("Mining sandstone...")
                Execution.delayUntil({ !it.isValid || Inventory.getEmptySlots() <= QuantumPlayerSense.ALMOST_EMPTY_INVENTORY_SLOTS.asInteger() || Npcs.newQuery().names("Guthixian butterfly").results().isNotEmpty() },
                        { Players.getLocal()?.animationId == -1 }, 5600, 6600)
            }
        }
    }
}
