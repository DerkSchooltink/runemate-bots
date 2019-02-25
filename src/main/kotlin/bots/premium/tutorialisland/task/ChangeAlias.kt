package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.script.Execution
import javafx.application.Platform

class ChangeAlias : ExtendedTask<TutorialIsland>() {

    override fun validate() = isTutorialIslandCompleted()

    override fun execute() {
        val currentAlias = Environment.getAccountAlias()
        val newAlias = bot.controller.activeAliases.firstOrNull { !bot.controller.handledAliases.contains(it) }
        if (newAlias == null) bot.stop("Out of accounts to run!") else {
            if (Environment.setAccount(newAlias)) {
                Execution.delay(5600, 8600)
                RuneScape.logout(false)
                Platform.runLater {
                    bot.controller.handledAliases.add(currentAlias)
                    bot.controller.activeAliases.remove(currentAlias)
                }
            }
        }
    }

    private fun isTutorialIslandCompleted() = Varp(281).value == 1000
}