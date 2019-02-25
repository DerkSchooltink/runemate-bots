package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Summoning : ExtendedTask<Citadel>() {

    private var obelisk: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        obelisk = GameObjects.newQuery().names("Obelisk").actions("Summon").results().nearest()
        return obelisk != null && player != null && player?.animationId == -1 && player?.isMoving == false
    }

    override fun execute() {
        logger.info("Summoning...")
        Execution.delay(1200, 1700)
        if (player?.animationId == -1) {
            if (obelisk?.isVisible == true) {
                if (obelisk?.interact("Summon") == true) {
                    logger.info("Delaying until action is done...")
                    Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                }
            } else {
                Camera.concurrentlyTurnTo(obelisk)
            }
        }
    }
}
