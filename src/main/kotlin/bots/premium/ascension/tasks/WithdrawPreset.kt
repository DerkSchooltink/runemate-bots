package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import quantum.api.core.ExtendedTask

class WithdrawPreset : ExtendedTask<Ascension>() {

    override fun validate() = Bank.isOpen()

    override fun execute() {
        logger.info("Withdrawing preset...")
        Bank.loadPreset(bot.ascensionSettings.preset, true)
    }
}
