package bots.free.mining.serenstone

import bots.free.mining.serenstone.ui.SerenStoneController
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class SerenStone : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()

    override fun onStart(vararg args: String) {
        embeddableUI = UIExtender(this, SerenStoneController(this), "com/bots/free/mining/serenstone/ui/serenstone_ui.fxml", listenerManager, breakHandler)
        setLoopDelay(1000, 1500)
    }
}
