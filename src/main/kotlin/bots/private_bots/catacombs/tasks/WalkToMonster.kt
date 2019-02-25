package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToMonster : ExtendedTask<CatacombsSlayer>() {

    override fun validate(): Boolean {
        val npc = Npcs.newQuery().names(bot.botSettings.monster?.name).results().nearest()
        return (npc == null || !npc.isVisible)
                && Distance.to(bot.botSettings.monster) > 10
                && Inventory.containsAnyOf(bot.botSettings.food.toString())
                && hasEnoughAttackPotions()
                && hasEnoughStrengthPotions()
    }

    override fun execute() {
        logger.info("Walking to monster...")
        PathBuilder.buildTo(bot.botSettings.monster)?.step()
    }

    private fun hasEnoughAttackPotions() = bot.botSettings.attackPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.attackPotion?.pattern)
    private fun hasEnoughStrengthPotions() = bot.botSettings.strengthPotionAmount == 0 || Inventory.containsAnyOf(bot.botSettings.strengthPotion?.pattern)
}
