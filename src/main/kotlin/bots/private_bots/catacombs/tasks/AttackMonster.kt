package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class AttackMonster : ExtendedTask<CatacombsSlayer>() {

    private val loot = arrayOf("Giant key", "Ancient shard", "Dark totem base", "Dark totem top", "Dark totem middle")
    private var monster: Npc? = null

    override fun validate(): Boolean {
        monster = Npcs.newQuery().names(bot.botSettings.monster?.toString()).filter { x -> (x.target == null || x.target == Players.getLocal()) && x.animationId != bot.botSettings.monster?.deathAnimation }.results().nearest()
        return GroundItems.newQuery().within(Area.Circular(Players.getLocal()?.position, 8.0)).names(*loot).results().isEmpty()
                && Inventory.containsAnyOf(bot.botSettings.food.toString())
                && monster != null
                && Players.getLocal()?.target == null
                && Players.getLocal()?.isMoving == false
                && Distance.to(bot.botSettings.monster?.coordinate) <= 10
    }

    override fun execute() {
        logger.info("Attacking " + bot.botSettings.monster?.toString() + "...")
        monster?.let {
            if (Distance.to(it) > 8) {
                BresenhamPath.buildTo(it)?.step()
            }
            if (it.turnAndInteract("Attack")) {
                Execution.delayUntil({ !it.isValid && it.healthGauge?.percent == 0 }, 3600, 3800)
            }
        }
    }
}
