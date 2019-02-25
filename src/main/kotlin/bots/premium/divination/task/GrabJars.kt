package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class GrabJars : ExtendedTask<HallOfMemories>() {

    override fun validate() = !Inventory.containsAnyOf(Pattern.compile(".*memory jar.*", Pattern.CASE_INSENSITIVE))

    override fun execute() {
        GameObjects.newQuery().names("Jar depot").actions("Take-from").results().nearest().let {
            if (it != null) {
                if (Distance.to(it) > 5) {
                    PathBuilder.buildTo(it)?.step()
                } else {
                    if (it.turnAndInteract("Take-from")) {
                        logger.info("Grabbing new jars...")
                        Execution.delayUntil({ Inventory.getEmptySlots() < 8 }, 1200, 3200)
                    } else {
                        it.turnAndInteract("Take-from")
                    }
                }
            } else {
                PathBuilder.buildTo(GameObjects.newQuery().names("Memory bud").results().first())?.step()
            }
        }
    }
}