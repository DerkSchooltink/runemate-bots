package bots.private_bots.username_grabber.task

import bots.private_bots.username_grabber.UsernameGrabber
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.local.WorldOverview
import com.runemate.game.api.hybrid.local.Worlds
import com.runemate.game.api.hybrid.local.hud.interfaces.WorldHop
import com.runemate.game.api.hybrid.region.Players
import java.util.*

class HopWorld : ExtendedTask<UsernameGrabber>() {

    private val list = Arrays.asList(106, 136, 137, 57, 115, 97, 120, 18, 33)

    private val nextWorld: WorldOverview?
        get() = Worlds
                .newQuery()
                .filter { w ->
                    (w.id != Worlds.getCurrent()
                            && !bot.handledWorlds.contains(w.id)
                            && !w.isSkillTotal2000
                            && !w.isSkillTotal1500
                            && !w.isSkillTotal2600
                            && w.isMembersOnly == bot.isP2p
                            && !list.contains(w.id))
                }
                .results()
                .first()

    override fun validate(): Boolean {
        return (RuneScape.isLoggedIn()
                && Players.getLocal() != null
                && hasCollectedAllUsersInCurrentWorld())
    }

    override fun execute() {
        val world = nextWorld

        if (nextWorld == null && bot.handledWorlds.size > 0) {
            logger.info("Clearing stored world list...")
            bot.handledWorlds.clear()
        }

        if (WorldHop.hopTo(world)) {
            logger.info("Hopping worlds...")
            world?.let { bot.addToHandledWorlds(it) }
        }
    }

    private fun hasCollectedAllUsersInCurrentWorld() = Players.newQuery()
            .results()
            .filterNotNull()
            .filter { player -> player != Players.getLocal() }
            .any { player -> bot.controller.usernameListView.items.contains(player.name) }
}
