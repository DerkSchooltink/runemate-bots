package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class PreciousOreSmithing : ExtendedTask<Citadel>() {

    private var oreContainer: GameObject? = null
    private var mould: GameObject? = null
    private var trough: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        mould = GameObjects.newQuery().names("Mould").actions("Empty").results().nearest()
        trough = GameObjects.newQuery().names("Water Trough", "Water trough").actions("Empty").results().nearest()
        oreContainer = GameObjects.newQuery().names("Precious Ore Container", "Precious Ore container").actions("Shovel").results().nearest()
        return (oreContainer != null || mould != null || trough != null) && player != null && player?.animationId == -1 && player?.isMoving == false
    }

    override fun execute() {
        if (oreContainer != null) {
            logger.info("Shoveling ores...")
            if (oreContainer?.isVisible == false) {
                Camera.concurrentlyTurnTo(oreContainer)
            } else {
                if (player?.animationId == -1) {
                    if (oreContainer?.interact("Shovel") == true) {
                        logger.info("Delaying until action is done...")
                        Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                    }
                }
            }
        } else if (mould != null) {
            logger.info("Emptying bars...")
            if (mould?.isVisible == false) {
                Camera.concurrentlyTurnTo(mould)
            } else {
                if (player?.animationId == -1) {
                    if (mould?.interact("Empty") == true) {
                        logger.info("Delaying until action is done...")
                        Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                    }
                }
            }
        } else if (trough != null) {
            logger.info("Moving bars...")
            if (trough?.isVisible == false) {
                Camera.concurrentlyTurnTo(trough)
            } else {
                if (player?.animationId == -1) {
                    if (trough?.interact("Empty") == true) {
                        logger.info("Delaying until action is done...")
                        Execution.delayUntil({ player?.animationId == -1 }, 1500, 2000)
                    }
                }
            }
        }
    }
}
