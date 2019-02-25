package bots.free.summoning

import bots.free.summoning.settings.BotSettings
import bots.free.summoning.ui.SummoningController
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class Summoning : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val botSettings = BotSettings()

    init {
        embeddableUI = UIExtender(this, SummoningController(this), "com/bots/free/summoning/ui/summoning_ui.fxml", listenerManager, breakHandler)
    }

    override fun onStart(vararg args: String) {
        setLoopDelay(1000, 1500)
    }
}
