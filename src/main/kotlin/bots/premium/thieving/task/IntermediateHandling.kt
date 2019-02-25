package bots.premium.thieving.task

import bots.premium.thieving.ElvesThieving
import bots.premium.thieving.data.Clan
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class IntermediateHandling : ExtendedTask<ElvesThieving>() {

    override fun validate() = !Inventory.isFull()
            && bot.settings.intermediateTask != null
            && Clan.values().none { it.isRequirementMet() && it.isThievable() }

    override fun execute() {
        bot.settings.intermediateTask?.let {
            val gameObject = GameObjects.newQuery().names(it.gameObject).actions(it.action).results().nearest()
            if (gameObject != null && Distance.to(gameObject) < 5) {
                if (gameObject.turnAndInteract(it.action)) {
                    logger.info("Interacting with ${it.gameObject}...")
                    Execution.delayUntil({ Players.getLocal()?.animationId == -1 }, { Players.getLocal()?.animationId != -1 }, 5600, 3600, 7600)
                } else {
                    gameObject.turnAndInteract(it.action)
                }
            } else {
                logger.info("Traversing to ${it.gameObject}...")
                PathBuilder.buildTo(it.location)?.step()
            }
        }
    }
}