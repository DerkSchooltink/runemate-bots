package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Cooking : ExtendedTask<Citadel>() {

    private var choppingTable: GameObject? = null

    override fun validate(): Boolean {
        choppingTable = GameObjects.newQuery().names("Large Chopping Board").actions("Chop").results().nearest()
        return choppingTable != null && Players.getLocal()?.animationId == -1
    }

    override fun execute() {
        logger.info("Cooking...")
        if (choppingTable.turnAndInteract("Chop")) {
            logger.info("Delaying until action is done...")
            Execution.delayUntil({ Players.getLocal()?.animationId == -1 }, 1500, 2000)
        }
    }
}
