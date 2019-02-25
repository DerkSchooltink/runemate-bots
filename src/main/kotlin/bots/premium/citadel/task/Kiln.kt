package bots.premium.citadel.task

import bots.premium.citadel.Citadel
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class Kiln : ExtendedTask<Citadel>() {

    private var kiln: GameObject? = null
    private var woodChips: GameObject? = null
    private var player: Player? = null

    override fun validate(): Boolean {
        player = Players.getLocal()
        kiln = GameObjects.newQuery().names("Kiln").appearance(36831, 36839).results().nearest()
        woodChips = GameObjects.newQuery().filter { Distance.to(kiln) < 3 }.names("Woodchip").actions("Shovel").results().nearest()

        return player != null && woodChips != null
    }

    override fun execute() {
        logger.info("Shoveling chips...")
        if (woodChips.turnAndInteract("Shovel")) {
            Execution.delayWhile({ kiln?.isValid }, { player?.animationId != -1 || player?.isMoving == true }, 100, 2000, 3000)
        }
    }
}
