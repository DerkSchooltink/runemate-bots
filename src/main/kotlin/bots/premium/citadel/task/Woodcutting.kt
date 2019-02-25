package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Woodcutting : ExtendedTask<Citadel>() {

    private var root: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        root = GameObjects.newQuery().names("Root").actions("Chop").results().nearest()
        return root != null && player != null && player?.animationId == -1
    }

    override fun execute() {
        logger.info("Woodcutting...")
        if (root?.isVisible == false) {
            Camera.concurrentlyTurnTo(root)
        } else {
            Execution.delay(1200, 1700)
            if (player?.animationId == -1) {
                if (root?.interact("Chop") == true) {
                    logger.info("Delaying until action is done...")
                    Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                }
            }
        }
    }
}
