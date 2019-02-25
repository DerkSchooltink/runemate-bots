package bots.premium.slayer.tasks.custom

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.frame.SlayerTask
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import java.util.regex.Pattern

class Strykewyrm : ExtendedTask<Slayer>() {

    private val strykeWyrmPattern = Pattern.compile(".*strykewyrm*\\w+")
    private var mound: Npc? = null

    private lateinit var strykewyrmResults: LocatableEntityQueryResults<Npc>

    override fun validate(): Boolean {
        mound = Npcs.newQuery().names("Mound").actions("Investigate").results().nearest()
        strykewyrmResults = Npcs.newQuery().names(strykeWyrmPattern).targeting(Players.getLocal()).results()

        return bot.slayerSettings.task?.amount != 0
                && bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.STRYKEWYRM
                && Distance.to(mound) < 20
                && (strykewyrmResults.isEmpty() || Players.getLocal()?.healthGauge == null)
                && bot.lootUpdater.getLootOrNull() == null
    }

    override fun execute() {
        SlayerTask.updateTask(bot.slayerSettings)
        Npcs.newQuery().targeting(Players.getLocal()).results().nearest().let {
            it?.turnAndInteract("Attack") ?: mound?.let { mound ->
                if (Distance.to(mound) > 15 || !mound.isVisible) PathBuilder.buildTo(mound)?.step()
                if (mound.turnAndInteract("Investigate")) {
                    val amount = Varps.getAt(183).value
                    val strykewyrmName = bot.slayerSettings.task?.names?.get(0)
                    logger.info("Attacking $strykewyrmName ($amount left)...")
                    Execution.delayUntil({ !strykewyrmResults.isEmpty() }, 1750, 2000)
                }
            }
        }
    }
}
