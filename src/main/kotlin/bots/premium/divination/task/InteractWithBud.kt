package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import quantum.api.game.twoTick
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.input.Mouse
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class InteractWithBud : ExtendedTask<HallOfMemories>() {

    private var bud: Npc? = null

    override fun validate(): Boolean {
        bud = Npcs.newQuery().names(*bot.settings.budNames).actions("Harvest").results().nearest()
        return bud != null
    }

    override fun execute() {
        if (Mouse.getSpeedMultiplier() != 1.0) Mouse.setSpeedMultiplier(1.0)
        bud?.let { bud ->
            if (Distance.to(bud) > 10) {
                PathBuilder.buildTo(bud)?.step()
            } else {
                if (bot.settings.twoTick) {
                    bud.twoTick { Inventory.containsAnyOf(*bot.settings.jarNames) }
                } else {
                    bud.turnAndInteract("Harvest") && Execution.delayUntil({ !bud.isValid }, { Players.getLocal()?.animationId != -1 }, 3400, 4500)
                }
            }
        }
    }
}