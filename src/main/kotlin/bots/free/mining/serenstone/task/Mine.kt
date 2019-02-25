package bots.free.mining.serenstone.task

import bots.free.mining.serenstone.SerenStone
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Mine : ExtendedTask<SerenStone>() {

    private var serenStone: GameObject? = null

    override fun validate(): Boolean {
        serenStone = GameObjects.newQuery().names("Seren stone").actions("Mine").results().nearest()
        return serenStone != null && Players.getLocal()?.animationId == -1
    }

    override fun execute() {
        serenStone?.let {
            if (Players.getLocal()?.animationId != -1 || it.turnAndInteract("Mine")) {
                Execution.delayUntil({ !it.isValid }, { Players.getLocal()?.animationId != -1 }, 2500, 3200, 3600)
            }
        }
    }
}