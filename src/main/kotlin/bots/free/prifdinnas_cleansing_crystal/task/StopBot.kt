package bots.free.prifdinnas_cleansing_crystal.task

import bots.free.prifdinnas_cleansing_crystal.PrifdinnasCleansingCrystal
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.rs3.local.hud.interfaces.MoneyPouch

class StopBot : ExtendedTask<PrifdinnasCleansingCrystal>() {

    override fun validate() = !Inventory.containsAnyOf("Cleansing crystal") && MoneyPouch.getContents() < 550000

    override fun execute() = bot.stop("Out of money!")
}
