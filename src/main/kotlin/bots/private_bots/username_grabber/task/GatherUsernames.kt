package bots.private_bots.username_grabber.task

import bots.private_bots.username_grabber.UsernameGrabber
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.entities.Player
import com.runemate.game.api.hybrid.queries.results.LocatableEntityQueryResults
import com.runemate.game.api.hybrid.region.Players

class GatherUsernames : ExtendedTask<UsernameGrabber>() {

    private var players: LocatableEntityQueryResults<Player>? = null

    override fun validate(): Boolean {
        players = Players.newQuery().results()
        return RuneScape.isLoggedIn() && Players.getLocal() != null && !hasCollectedAllUsersInCurrentWorld()
    }

    override fun execute() {
        logger.info("Gathering usernames...")
        players?.forEach { player ->
            player.name.let {
                if (!it.isNullOrBlank() && player != Players.getLocal())
                    if (!bot.controller.usernameListView.items.contains(it)) {
                        bot.addNameToListView(it)
                        bot.textFileUpdater(it)
                    }
            }
        }
    }

    private fun hasCollectedAllUsersInCurrentWorld() = if (!bot.isHopWorlds) false else Players.newQuery()
            .results()
            .filterNotNull()
            .filter { player -> player != Players.getLocal() }
            .any { bot.controller.usernameListView.items.contains(it.name) }
}
