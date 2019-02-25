package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class BankHandler : ExtendedTask<TutorialIsland>() {

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 14 || it == 15 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            510 -> openBank()
            520 -> checkPollBooth()
            525 -> openDoorAtPosition(Coordinate(3125, 3124, 0), 525)
            530 -> bot.talkToInstructor("Financial Advisor")
            540 -> openDoorAtPosition(Coordinate(3130, 3124, 0), 540)
        }
    }

    private fun openDoorAtPosition(position: Coordinate, progress: Int): Boolean {
        val door = GameObjects.newQuery().names("Door").on(position).actions("Open").results().nearest()
        return door?.turnAndInteract("Open") == true && Execution.delayUntil({ bot.getProgress().get() != progress }, 1200, 1800)
    }

    private fun openBank() {
        ChatDialog.getContinue()?.select()
        ChatDialog.getOption("Yes.")?.select()

        if (bot.getContinueInterface()?.isVisible == true) bot.getContinueInterface()?.click() else {
            GameObjects.newQuery().names("Bank booth").results().nearest().let {
                if (it == null) {
                    RegionPath.buildTo(Coordinate(3121, 3118, 0))?.step()
                } else {
                    if (Distance.to(it) > 5) {
                        PathBuilder.buildTo(it)?.step()
                    }
                    if (it.position.isReachable) {
                        it.turnAndInteract("Use")
                        Execution.delayWhile { Players.getLocal()?.isMoving }
                    } else {
                        GameObjects.newQuery().names("Large door").actions("Open").results().nearest()?.turnAndInteract("Open")
                    }
                }
            }
        }
    }

    private fun checkPollBooth() {
        if (Bank.isOpen()) Bank.close() else if (DepositBox.isOpen()) DepositBox.close()
        if (ChatDialog.getContinue() == null) {
            GameObjects.newQuery().names("Poll booth").actions("Use").results().nearest()?.turnAndInteract("Use")
            Execution.delayWhile { Players.getLocal()?.isMoving }
        } else ChatDialog.getContinue()?.select()
    }
}
