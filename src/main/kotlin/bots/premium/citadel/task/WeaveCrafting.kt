package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class WeaveCrafting : ExtendedTask<Citadel>() {

    private var loom: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        loom = GameObjects.newQuery().names("Loom").actions("Weave").results().nearest()
        return loom != null && player != null && player?.animationId == -1 && player?.isMoving == false
    }

    override fun execute() {
        logger.info("Crafting...")
        if (loom?.isVisible == false) {
            Camera.concurrentlyTurnTo(loom)
        } else {
            Execution.delay(5000, 6500)
            if (player?.animationId == -1) {
                if (loom?.interact("Weave") == true) {
                    logger.info("Delaying until action is done...")
                    Execution.delayUntil({ player?.animationId == -1 }, 5000, 6500)
                }
            }
        }
    }
}
