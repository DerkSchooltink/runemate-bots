package bots.free.runespan.tasks

import bots.free.runespan.Runespan
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class Siphon : ExtendedTask<Runespan>() {

    private var entity: LocatableEntity? = null

    override fun validate(): Boolean {
        entity = GameObjects.newQuery().reachable().names(*bot.entityNames.toTypedArray()).actions("Siphon").results().nearest()
                ?: Npcs.newQuery().names(*bot.creatureNames.toTypedArray()).actions("Siphon").reachable().results().nearest()
        return Inventory.containsAnyOf("Rune essence")
                && entity != null
                && Players.getLocal()?.run { animationId == -1 && !isMoving } == true
                && GroundItems.newQuery().names("Anagogic ort").reachable().results().isEmpty()
    }

    override fun execute() {
        logger.info("Siphoning...")
        if (Distance.to(entity) > QuantumPlayerSense.AVERAGE_DISTANCE_TO_ENTITY.asInteger()) {
            BresenhamPath.buildTo(entity)?.step()
        } else if (entity.turnAndInteract("Siphon")) {
            Execution.delayUntil({ Players.getLocal()?.animationId == -1 }, 1200, 1800)
        }
    }
}

