package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class ActivatePrayer : ExtendedTask<Ascension>() {

    override fun validate() = bot.ascensionSettings.isUsingPrayer
            && !Powers.Prayer.isQuickPraying()
            && !Npcs.newQuery().names("Rorarius").results().isEmpty()
            && Health.getCurrentPercent() < QuantumPlayerSense.EMERGENCY_HEALTH_AMOUNT.asInteger()
            && Powers.Prayer.getPoints() != 0

    override fun execute() {
        logger.info("Activating prayer...")
        Powers.Prayer.toggleQuickPrayers()
    }
}
