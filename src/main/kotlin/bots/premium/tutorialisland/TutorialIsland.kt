package bots.premium.tutorialisland

import bots.premium.tutorialisland.ui.TutorialIslandController
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import javafx.beans.property.SimpleIntegerProperty
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract

class TutorialIsland : ExtendedTaskBot() {

    val controller = TutorialIslandController(this)

    private val interfaceStrings = arrayOf("Nothing interesting happens.", "I can't reach that!", "You retrieve a bar of bronze.")

    override fun onStart(vararg arguments: String?) {
        setLoopDelay(600, 1200)
        embeddableUI = UIExtender(this, controller, "com/bots/premium/tutorialisland/ui/tutorial_ui.fxml")
    }

    fun talkToInstructor(name: String, coordinate: Coordinate? = Players.getLocal()?.position): Boolean {
        Npcs.newQuery().names(name).results().nearest()?.let {
            if (!it.isVisible) {
                return PathBuilder.buildTo(coordinate)?.step() == true
            } else {
                when {
                    getContinueInterface()?.isVisible == true -> getContinueInterface()?.click()
                    ChatDialog.getContinue() != null -> ChatDialog.getContinue()?.select()
                    ChatDialog.getOption("I am an experienced player.") != null -> ChatDialog.getOption("I am an experienced player.")?.select()
                    else -> return if (it.turnAndInteract("Talk-to")) {
                        Execution.delayUntil(::pendingContinue, 1200, 1800)
                    } else false
                }
            }
        }
        return false
    }

    fun getContinueInterface() = Interfaces.newQuery().texts(*interfaceStrings).results().first()
    fun pendingContinue() = ChatDialog.getContinue() != null
    fun getProgress() = SimpleIntegerProperty(Varp(281).value)
}
