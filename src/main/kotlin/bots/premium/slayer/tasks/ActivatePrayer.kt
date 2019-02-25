package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class ActivatePrayer : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.useQuickPrayer
            && !Powers.Prayer.isQuickPraying()
            && !Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isEmpty()
            && Health.getCurrentPercent() < 70
            && Powers.Prayer.getPoints() != 0

    override fun execute() {
        logger.info("Activating prayer...")
        Powers.Prayer.toggleQuickPrayers()
    }
}
