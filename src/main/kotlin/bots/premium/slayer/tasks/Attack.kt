package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.Monster
import bots.premium.slayer.data.SlayerCombatStyle
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.location.navigation.basic.ViewportPath
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.turnAndInteract

class Attack : ExtendedTask<Slayer>() {

    override fun validate() = !Inventory.isFull()
            && SlayerCombatStyle.isUsingRegularCombatStyle(bot.slayerSettings.task?.requirements?.styleSlayer)
            && bot.slayerSettings.task?.amount != 0
            && ActionBar.isAutoRetaliating()
            && Players.getLocal()?.target == null
            && hasRequirements()
            && antifireIsUsed()
            && closeToMonsters()
            && monsterIsNotOnSkipList(bot.slayerSettings.task?.monster)
            && bot.lootUpdater.getLootOrNull() == null

    override fun execute() {
        getSlayerNpc()?.let {
            if (Distance.to(it) > 15 && it.visibility < 50) {
                ViewportPath.convert(BresenhamPath.buildTo(it))?.step()
            } else {
                if (bot.slayerSettings.task?.monster?.forcedModel != null) it.setForcedModel(bot.slayerSettings.task?.monster?.forcedModel)
                if (it.turnAndInteract("Attack")) {
                    val amount = Varps.getAt(183).value
                    logger.info("Attacking ${it.name} ($amount left)...")
                } else it.turnAndInteract("Attack")
            }
        }
    }

    private fun antifireIsUsed() = bot.slayerSettings.task?.requirements?.styleSlayer != SlayerCombatStyle.DRAGON ||
            bot.slayerSettings.antifirePotion?.getVarbit()?.value != 0

    private fun hasRequirements() = bot.slayerSettings.task?.monster?.requirements?.hasEquipment() == true

    private fun monsterIsNotOnSkipList(monster: Monster?) = !bot.slayerSettings.skipTasks.contains(monster)

    private fun closeToMonsters() = Distance.to(bot.slayerSettings.task?.monster?.location) <= 20 ||
            Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isNotEmpty()

    private fun getSlayerNpc() = Players.getLocal()?.target ?: Npcs.newQuery()
        .names(bot.slayerSettings.task?.names)
        .actions("Attack")
        .filter { npc -> (npc.target == Players.getLocal() || npc.target == null) && bot.slayerSettings.task?.deathAnimations?.contains(npc.animationId) == false }
        .withinRadius(25)
        .surroundingsReachable()
        .results()
        .nearest()
}

