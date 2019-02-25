package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.rs3.local.hud.Powers

class DeactivatePrayer : ExtendedTask<Muspah>() {

    override fun validate() = (healthSufficient() || npcNotNearby()) && Powers.Prayer.isQuickPraying()

    override fun execute() {
        logger.info("Deactivating quickprayer...")
        Powers.Prayer.toggleQuickPrayers()
    }

    private fun healthSufficient() = Health.getCurrentPercent() > Random.nextInt(95, 99)

    private fun npcNotNearby() = Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
}
