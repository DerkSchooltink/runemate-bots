package bots.premium.slayer.tasks.custom

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class Mogre : ExtendedTask<Slayer>() {

    private var mogre: Npc? = null
    private var bubbles: GameObject? = null

    override fun validate(): Boolean {
        bubbles = GameObjects.newQuery().names("Ominous Fishing Spot").results().nearest()
        mogre = Npcs.newQuery().targetedBy(Players.getLocal()).targeting(Players.getLocal()).names(bot.slayerSettings.task?.names).results().nearest()

        return (bot.slayerSettings.task?.amount != 0 && bot.slayerSettings.task?.requirements?.styleSlayer == SlayerCombatStyle.MOGRE
                && ((mogre != null && Distance.to(mogre) < 20)) || (bubbles != null && Distance.to(bubbles) < 15))
    }

    override fun execute() {
        SlayerTask.updateTask(bot.slayerSettings)
        if (mogre != null) {
            mogre?.let {
                if (!it.isVisible) {
                    Camera.concurrentlyTurnTo(it)
                }
                if (it.interact("Attack")) {
                    logger.info("Attacking mogre...")
                    Execution.delayUntil({ !it.isValid }, 1000, 2000)
                }
            }
        } else if (bubbles != null) {
            bubbles?.let {
                if (!it.isVisible) {
                    Camera.concurrentlyTurnTo(it)
                }
                if (it.interact("Explode!")) {
                    logger.info("Interacting with bubbles...")
                    Execution.delayUntil({ Npcs.newQuery().names("Mogre").targeting(Players.getLocal()).results().isNotEmpty() }, 1200, 1800)
                }
            }
        }
    }
}
