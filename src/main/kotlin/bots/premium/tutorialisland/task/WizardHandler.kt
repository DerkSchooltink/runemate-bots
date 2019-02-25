package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.osrs.local.hud.interfaces.Magic

class WizardHandler : ExtendedTask<TutorialIsland>() {

    private val wizardCoordinate = Coordinate(3141, 3086, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 18 || it == 19 || it == 20 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            620 -> goToWizard()
            630 -> ControlPanelTab.MAGIC.open()
            640 -> bot.talkToInstructor("Magic Instructor", wizardCoordinate)
            650 -> attackChicken()
            670 -> if (Magic.WIND_STRIKE.isSelected) Magic.WIND_STRIKE.deactivate() else handleDialog()
        }
    }

    private fun goToWizard() = if (Distance.to(wizardCoordinate) > 10) {
        PathBuilder.buildTo(wizardCoordinate)?.step()
    } else {
        bot.talkToInstructor("Magic Instructor", wizardCoordinate)
    }

    private fun handleDialog() = ChatDialog.getOptions().find { it.text == "Yes." || it.text == "No, I'm not planning to do that." }.let {
        it?.select() ?: bot.talkToInstructor("Magic Instructor")
    }

    private fun attackChicken() = Npcs.newQuery().names("Chicken").results().nearest().let {
        if (it == null || !it.isVisible) {
            PathBuilder.buildTo(wizardCoordinate)?.step() == true
        } else {
            if (Magic.WIND_STRIKE.isSelected) {
                Camera.concurrentlyTurnTo(it)
                it.click()
            } else {
                Magic.WIND_STRIKE.activate()
            }
        }
    }
}
