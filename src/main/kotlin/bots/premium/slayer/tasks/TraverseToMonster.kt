package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.Monster
import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance

class TraverseToMonster : ExtendedTask<Slayer>() {

    override fun validate() = !closeToMonsters()
            && bot.slayerSettings.task?.amount != 0
            && hasFood()
            && hasAntifire()
            && monsterIsSupported()
            && Inventory.getEmptySlots() > 1
            && monsterIsNotOnSkipList()

    override fun execute() {
        SlayerTask.updateTask(bot.slayerSettings)
        logger.info("Walking towards monsters...")
        PathBuilder.buildTo(getCorrectLocation())?.also { println(it) }?.step()
    }

    private fun closeToMonsters() = Distance.to(bot.slayerSettings.task?.monster?.location) <= 20 || Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isNotEmpty()
    private fun monsterIsNotOnSkipList() = !bot.slayerSettings.skipTasks.contains(bot.slayerSettings.task?.monster)
    private fun monsterIsSupported() = !Monster.unsupportedMonsters.contains(bot.slayerSettings.task?.monster)
    private fun hasAntifire() = bot.slayerSettings.task?.requirements?.styleSlayer != SlayerCombatStyle.DRAGON || !Inventory.newQuery().names(bot.slayerSettings.antifirePotion?.pattern).results().isEmpty()
    private fun hasFood() = Health.getCurrentPercent() > 50 && !Inventory.newQuery().actions("Eat").results().isEmpty()
    private fun getCorrectLocation(): Locatable? = when {
        bot.slayerSettings.task?.monster?.lowLevelCoordinate != null && Players.getLocal()?.combatLevel?.let { it < 50 } == true -> bot.slayerSettings.task?.monster?.lowLevelCoordinate
        else -> bot.slayerSettings.task?.monster
    }
}
