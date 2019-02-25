package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.useOn
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution

class CookingHandler : ExtendedTask<TutorialIsland>() {

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 4 || it == 5 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            130 -> openFirstDoor()
            140 -> bot.talkToInstructor("Master Chef")
            150 -> makeDough()
            160 -> bakeDough()
            170 -> ControlPanelTab.MUSIC_PLAYER.open()
            180 -> openSecondDoor()
        }
    }

    private fun openFirstDoor() {
        val door = GameObjects.newQuery().names("Door").actions("Open").results().nearest()
        if (Distance.to(door) < 3) {
            Camera.concurrentlyTurnTo(door)
            door?.interact("Open")
            Execution.delayWhile { Players.getLocal()?.isMoving }
        } else BresenhamPath.buildTo(door)?.step()
    }

    private fun openSecondDoor() {
        val door = GameObjects.newQuery().names("Door").actions("Open").on(Coordinate(3072, 3090, 0)).results().nearest()
        if (Distance.to(door) < 3) {
            Camera.concurrentlyTurnTo(door)
            door?.interact("Open")
            Execution.delayWhile { Players.getLocal()?.isMoving }
        } else BresenhamPath.buildTo(door)?.step()
    }

    private fun makeDough(): Boolean {
        val flour = Inventory.newQuery().names("Pot of flour").results().first()
        val water = Inventory.newQuery().names("Bucket of water").results().first()

        return flour.useOn(water) && Execution.delayUntil({ Inventory.contains("Bread dough") }, 1200, 1800)
    }

    private fun bakeDough(): Boolean {
        val dough = Inventory.newQuery().names("Bread dough").results().first()
        val range = GameObjects.newQuery().names("Range").results().nearest()

        return if (Inventory.getSelectedItem() != dough) {
            dough?.interact("Use") == true && range?.click() == true && Execution.delayUntil({ Inventory.contains("Bread") }, 1200, 1800)
        } else {
            range?.click() == true && Execution.delayUntil({ Inventory.contains("Bread") }, 1200, 1800)
        }
    }
}
