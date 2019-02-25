package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import bots.premium.divination.data.Memory
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import quantum.api.game.twoTick
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.input.Mouse
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class SiphonMemories : ExtendedTask<HallOfMemories>() {

    private var memory: Npc? = null

    override fun validate(): Boolean {
        memory = getMemory(*Memory.harvestableMemories().toTypedArray())
        return Inventory.containsAnyOf(*bot.settings.jarNames) && Players.getLocal()?.animationId == -1
                && Npcs.newQuery().names(*bot.settings.fragmentNames).actions("Capture").results().isEmpty()
                && Npcs.newQuery().names(*bot.settings.budNames).actions("Harvest").results().isEmpty()
    }

    override fun execute() {
        if (Mouse.getSpeedMultiplier() != 1.0) Mouse.setSpeedMultiplier(1.0)
        memory.let {
            if (it != null) {
                if (bot.settings.twoTick) {
                    memory.twoTick { Inventory.containsAnyOf(*bot.settings.jarNames) }
                } else {
                    it.turnAndInteract("Harvest") && Execution.delayUntil({ !it.isValid }, 600, 1200)
                }
            } else {
                PathBuilder.buildTo(GameObjects.newQuery().names("Memory bud").results().first())?.step()
            }
        }
    }

    private fun getMemory(vararg memories: Memory) = Npcs
            .newQuery()
            .names(*memories.map { it.toString() }.toTypedArray())
            .filter(Npcs.getNamePredicate("Faded memories").negate())
            .actions("Harvest")
            .results()
            .sortedWith(compareByDescending<Npc?> { Memory.getConstant(it?.name.toString()).divinationLevel }.thenBy { Distance.to(it) })
            .firstOrNull()
}