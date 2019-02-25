package bots.free.mining.sandstone.task

import bots.free.mining.sandstone.Sandstone
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.Varbits
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class DrinkJuju : ExtendedTask<Sandstone>() {

    private val jujuPotionPattern = Pattern.compile("juju mining", Pattern.CASE_INSENSITIVE)

    override fun validate() = bot.botSettings.isUsingJujuPotions && !jujuActive()

    override fun execute() {
        Inventory.newQuery().names(jujuPotionPattern).results().first()?.let {
            if (it.interact("Drink")) {
                logger.info("Drinking juju potion...")
                Execution.delayUntil(::jujuActive, 1200, 1800)
            }
        }
    }

    private fun jujuActive(): Boolean {
        val perfectJuju = Varbits.load(25855)
        val normalJuju = Varbits.load(25863)
        return perfectJuju != null && normalJuju != null && (normalJuju.value > 0 || perfectJuju.value > 0)
    }
}
