package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class AddToPlinth : ExtendedTask<HallOfMemories>() {

    override fun validate() = Inventory.contains("Core memory fragment") && Npcs.newQuery().names(*bot.settings.budNames).results().isEmpty()

    override fun execute() {
        GameObjects.newQuery().names("Plinth").actions("Interact").results().nearest()?.let {
            if (Distance.to(it) > 10) {
                PathBuilder.buildTo(it)?.step()
            } else {
                if (it.turnAndInteract("Interact")) {
                    Execution.delayUntil({ !it.isValid }, { Players.getLocal()?.isMoving }, 1200, 1600)
                } else {
                    it.turnAndInteract("Interact")
                }
            }
        }
    }
}