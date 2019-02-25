package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.script.Execution
import java.util.*

class Loot : ExtendedTask<Ascension>() {

    private lateinit var groundItems: LocatableEntityQueryResults<GroundItem>

    override fun validate(): Boolean {
        if (Inventory.isFull()) return false

        val lootSet = bot.ascensionSettings.loot.toMutableSet()

        if (bot.ascensionSettings.isUsingInvention) {
            lootSet += bot.ascensionSettings.inventionloot
        }

        groundItems = GroundItems.newQuery().withinRadius(20.0).names(*lootSet.toTypedArray()).results()

        return !groundItems.isEmpty()
    }

    override fun execute() {
        logger.info("Looting...")
        val item = groundItems.stream().filter { Objects.nonNull(it) }?.findFirst()?.orElse(null) ?: return

        if (item.turnAndInteract("Take")) {
            Execution.delayUntil({ !item.isValid }, 600, 800)
        }
    }
}
