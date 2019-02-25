package bots.free.prifdinnas_bonfire.task

import bots.free.prifdinnas_bonfire.PrifdinnasBonfire
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import java.util.regex.Pattern

class WithdrawPreset : ExtendedTask<PrifdinnasBonfire>() {

    override fun validate() = Bank.isOpen() && !Inventory.containsAnyOf(Pattern.compile(".* logs.*", Pattern.CASE_INSENSITIVE))

    override fun execute() {
        logger.info("Withdrawing preset ${bot.preset}...")
        bot.listenerManager.freezeListener()
        Bank.loadPreset(bot.preset)
    }
}
