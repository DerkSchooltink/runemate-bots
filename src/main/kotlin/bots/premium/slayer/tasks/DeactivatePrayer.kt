package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.rs3.local.hud.Powers

class DeactivatePrayer : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.useQuickPrayer && (healthSufficient() || npcNotNearby()) && Powers.Prayer.isQuickPraying()

    override fun execute() {
        logger.info("Deactivating quickprayer...")
        Powers.Prayer.toggleQuickPrayers()
    }

    private fun healthSufficient() = Health.getCurrentPercent() > Random.nextInt(95, 99)
    private fun npcNotNearby() = bot.slayerSettings.task?.run { Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isEmpty() } == true
}
