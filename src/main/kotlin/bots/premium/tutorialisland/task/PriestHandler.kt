package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution

class PriestHandler : ExtendedTask<TutorialIsland>() {

    private val brotherBraceCoordinate = Coordinate(3129, 3106, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 16 || it == 17 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            550 -> handleDoor()
            560 -> ControlPanelTab.PRAYER.open()
            570 -> bot.talkToInstructor("Brother Brace", brotherBraceCoordinate)
            580 -> ControlPanelTab.FRIENDS_LIST.open()
            590 -> ControlPanelTab.IGNORE_LIST.open()
            600 -> bot.talkToInstructor("Brother Brace", brotherBraceCoordinate)
            610 -> openSmallDoor()
        }
    }

    private fun handleDoor() {
        val door = GameObjects.newQuery().names("Large door").actions("Open").results().nearest()
        val monk = Npcs.newQuery().names("Brother Brace").results().nearest()
        if (Distance.to(brotherBraceCoordinate) > 8) PathBuilder.buildTo(brotherBraceCoordinate)?.step() else {
            if (monk?.position?.isReachable == true) {
                bot.talkToInstructor("Brother Brace")
            } else if (door?.turnAndInteract("Open") == true) Execution.delayWhile { Players.getLocal()?.isMoving }
        }
    }

    private fun openSmallDoor() {
        GameObjects.newQuery().names("Door").actions("Open").results().nearest()?.let {
            if (it.turnAndInteract("Open")) {
                Execution.delayWhile { Players.getLocal()?.isMoving }
            }
        }
    }
}
