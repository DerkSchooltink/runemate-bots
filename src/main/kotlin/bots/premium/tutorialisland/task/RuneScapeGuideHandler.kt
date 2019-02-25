package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.osrs.local.hud.interfaces.OptionsTab
import com.runemate.game.api.script.Execution

class RuneScapeGuideHandler : ExtendedTask<TutorialIsland>() {

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 0 || it == 1 }
    }

    override fun execute() {
        if (getCharacterSelectionScreen() != null) getCharacterSelectionScreen()?.interact("Accept") else {
            ChatDialog.getContinue()?.select()
            when (bot.getProgress().get()) {
                0 -> bot.talkToInstructor("RuneScape Guide")
                3 -> ControlPanelTab.OPTIONS.open()
                7 -> bot.talkToInstructor("RuneScape Guide")
                10 -> hideRoofs() && openDoor()
            }
        }
    }

    private fun hideRoofs() = if (!OptionsTab.areRoofsAlwaysHidden()) OptionsTab.toggleAlwaysHideRoofs() && OptionsTab.closeAdvancedOptionsMenu() else true

    private fun openDoor() = GameObjects.newQuery().names("Door").actions("Open").results().nearest()?.interact("Open") == true
            && Execution.delayUntil({ GameObjects.newQuery().names("Oak").reachable().results().isNotEmpty() }, 1200, 1400)

    private fun getCharacterSelectionScreen() = Interfaces.newQuery().actions("Accept").results().first()
}
