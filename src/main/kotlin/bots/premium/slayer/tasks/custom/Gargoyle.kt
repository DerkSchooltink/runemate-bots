package bots.premium.slayer.tasks.custom

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class Gargoyle : ExtendedTask<Slayer>() {

    private var gargoyle: Npc? = null

    override fun validate(): Boolean {
        gargoyle = Npcs.newQuery().filter { (it.target == null || it.target == Players.getLocal()) }.names("Gargoyle").results().nearest()
        return bot.slayerSettings.task?.amount != 0 && bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.GARGOYLE
                && (Distance.to(gargoyle) < 20 || gargoyle?.isVisible == true)

    }

    override fun execute() {
        SlayerTask.updateTask(bot.slayerSettings)
        gargoyle?.let {
            if (it.healthGauge == null || it.healthGauge?.run { percent > 5 } == true) {
                if (it.turnAndInteract("Attack")) {
                    logger.info("Attacking ${it.name} (${Varps.getAt(183).value} left)...")
                    Execution.delayUntil({ it.healthGauge?.run { percent > 5 } == false }, 1000, 2000)
                }
            } else if (it.turnAndInteract("Smash")) {
                logger.info("Finishing gargoyle...")
                Execution.delayUntil({ !it.isValid }, 1000, 2000)
            }
        }
    }
}
