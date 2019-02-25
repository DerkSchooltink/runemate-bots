package bots.free.summoning

import bots.free.summoning.settings.BotSettings
import bots.free.summoning.ui.SummoningController
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class Summoning : ExtendedTaskBot() {

    val botSettings = BotSettings()

    init {
        embeddableUI = UIExtender(this, SummoningController(this), "com/bots/free/summoning/ui/summoning_ui.fxml")
    }

    override fun onStart(vararg args: String) {
        setLoopDelay(1000, 1500)
    }
}
