package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank

class WithdrawPreset : ExtendedTask<Ascension>() {

    override fun validate() = Bank.isOpen()

    override fun execute() {
        logger.info("Withdrawing preset...")
        bot.listenerManager.freezeListener()
        Bank.loadPreset(bot.ascensionSettings.preset, true)
    }
}
