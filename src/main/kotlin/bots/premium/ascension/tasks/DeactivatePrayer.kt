package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers

class DeactivatePrayer : ExtendedTask<Ascension>() {

    private val isNearMonsters: Boolean = !Npcs.newQuery().names("Rorarius").results().isEmpty()

    override fun validate() = Powers.Prayer.isQuickPraying() && hasSufficientHealth() && isNearMonsters

    override fun execute() {
        logger.info("Deactivating quickprayer...")
        Powers.Prayer.toggleQuickPrayers()
    }

    private fun hasSufficientHealth() = !bot.ascensionSettings.isUsingPrayer || Health.getCurrentPercent() > 95
}
