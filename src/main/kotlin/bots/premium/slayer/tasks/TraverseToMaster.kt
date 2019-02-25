package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.region.Npcs

class TraverseToMaster : ExtendedTask<Slayer>() {

    override fun validate() = !bot.slayerSettings.usingNPCContact
            && bot.slayerSettings.task.let { it == null || it.amount == 0 }
            && noSlayerMasterNearby()

    override fun execute() {
        logger.info("Walking towards slayermaster...")
        PathBuilder.buildTo(bot.slayerSettings.slayerMaster)?.step()
    }

    private fun noSlayerMasterNearby() = Npcs.newQuery().names(bot.slayerSettings.slayerMaster?.masterName).visible().results().isEmpty()
}
