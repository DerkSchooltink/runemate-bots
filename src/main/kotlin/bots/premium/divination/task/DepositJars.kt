package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class DepositJars : ExtendedTask<HallOfMemories>() {

    private var unstableRift: GameObject? = null

    override fun validate(): Boolean {
        unstableRift = GameObjects.newQuery().names("Unstable rift").actions("Offer-memory").results().nearest()
        return unstableRift != null && !Inventory.containsAnyOf(*bot.settings.jarNames) && Inventory.contains("Memory jar (full)")
    }

    override fun execute() {
        logger.info("Depositing jars...")
        unstableRift?.let {
            if (Distance.to(it) < 10 || it.isVisible) {
                if (it.turnAndInteract("Offer-memory")) {
                    Execution.delayUntil({ !Inventory.containsAnyOf("Memory jar (full)") }, { Players.getLocal()?.animationId != -1 }, 1800, 3600)
                } else {
                    it.turnAndInteract("Offer-memory")
                }
            } else {
                PathBuilder.buildTo(it)?.step()
            }
        }
    }
}