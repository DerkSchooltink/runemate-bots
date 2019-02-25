package bots.premium.slayer.tasks.custom

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class DesertLizard : ExtendedTask<Slayer>() {

    private var desertLizard: Npc? = null

    override fun validate(): Boolean {
        desertLizard = Npcs.newQuery().names(bot.slayerSettings.task?.names).filter { it.target == null || it.target == Players.getLocal() }.results().nearest()
        return bot.slayerSettings.task?.amount != 0 && bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.DESERT_LIZARD
                && (Distance.to(desertLizard) < 20 || desertLizard?.isVisible == true)
    }

    override fun execute() {
        SlayerTask.updateTask(bot.slayerSettings)
        desertLizard?.let {
            if (it.healthGauge == null || it.healthGauge?.run { percent > 5 } == true) {
                if (it.turnAndInteract("Attack")) {
                    Execution.delayUntil({ it.healthGauge?.run { percent > 5 } == false }, 1000, 2000)
                }
            } else if (it.turnAndInteract("Cool off")) {
                logger.info("Finishing lizard...")
                Execution.delayUntil({ !it.isValid || bot.slayerSettings.task?.deathAnimations?.contains(it.animationId) == true }, 1000, 2000)
            }
        }
    }
}
