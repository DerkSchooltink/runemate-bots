package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.Traversal
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution

class QuestHandler : ExtendedTask<TutorialIsland>() {

    private val tutorCoordinate = Coordinate(3086, 3126, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 6 || it == 7 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            183 -> ControlPanelTab.EMOTES.open()
            187 -> getRandomEmote()?.click()
            190 -> ControlPanelTab.OPTIONS.open()
            200 -> if (!Traversal.isRunEnabled()) Traversal.toggleRun()
            210 -> moveToQuestTutor()
            220 -> bot.talkToInstructor("Quest Guide", Players.getLocal()?.position)
            230 -> ControlPanelTab.QUEST_LIST.open()
            240 -> bot.talkToInstructor("Quest Guide", Players.getLocal()?.position)
            250 -> goDownLadder()
        }
    }

    private fun getRandomEmote() = Interfaces.newQuery().actions("Jig").results().first()

    private fun goDownLadder() {
        val ladder = GameObjects.newQuery().names("Ladder").actions("Climb-down").results().nearest()
        if (ladder.turnAndInteract("Climb-down")) {
            Execution.delayWhile { Players.getLocal()?.isMoving }
        }
    }

    private fun moveToQuestTutor() {
        if (Distance.to(tutorCoordinate) < 8) {
            openDoor()
        } else {
            if (Npcs.newQuery().names("Quest Guide").reachable().results().isEmpty()) {
                PathBuilder.buildTo(tutorCoordinate)?.step()
            } else openDoor()
        }
    }

    private fun openDoor() {
        val door = GameObjects.newQuery().names("Door").actions("Open").results().nearest()
        if (Distance.to(door) < 3) {
            if (door.turnAndInteract("Open")) {
                Execution.delayWhile { Players.getLocal()?.isMoving }
            }
        } else RegionPath.buildTo(Coordinate(3086, 3126, 0))?.step()
    }
}
