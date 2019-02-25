package bots.free.prifdinnas_bonfire.task

import bots.free.prifdinnas_bonfire.PrifdinnasBonfire
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import java.util.regex.Pattern

class OpenBank : ExtendedTask<PrifdinnasBonfire>() {

    override fun validate() = !Inventory.containsAnyOf(Pattern.compile(".* logs.*", Pattern.CASE_INSENSITIVE)) && !Bank.isOpen()

    override fun execute() {
        logger.info("Opening bank...")
        Bank.open()
    }
}
