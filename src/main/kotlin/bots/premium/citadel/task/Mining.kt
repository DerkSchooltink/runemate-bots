package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Mining(private val rockName: String) : ExtendedTask<Citadel>() {

    private var oreRock: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        oreRock = GameObjects.newQuery().names(rockName).actions("Mine").results().nearest()
        return oreRock != null && player != null && player?.animationId == -1 && player?.isMoving == false
    }

    override fun execute() {
        logger.info("Mining $rockName...")
        if (oreRock?.isVisible == false) {
            Camera.concurrentlyTurnTo(oreRock)
        } else {
            Execution.delay(2500, 3500)
            if (player?.animationId == -1) {
                if (oreRock?.interact("Mine") == true) {
                    logger.info("Delaying until action is done...")
                    Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                }
            }
        }
    }
}
