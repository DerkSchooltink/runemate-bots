package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class ActivatePrayer : ExtendedTask<Muspah>() {

    override fun validate() = !Powers.Prayer.isQuickPraying()
            && !Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
            && Health.getCurrentPercent() < 85
            && Powers.Prayer.getPoints() != 0

    override fun execute() {
        logger.info("Activating prayer...")
        Powers.Prayer.toggleQuickPrayers()
    }
}
