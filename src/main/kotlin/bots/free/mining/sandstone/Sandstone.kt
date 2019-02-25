package bots.free.mining.sandstone

import bots.free.mining.sandstone.settings.BotSettings
import bots.free.mining.sandstone.ui.SandstoneUI
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class Sandstone : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val botSettings = BotSettings()

    override fun onStart(vararg args: String) {
        QuantumPlayerSense.initialize()
        embeddableUI = UIExtender(this, SandstoneUI(this), "com/bots/free/mining/sandstone/ui/sandstone_ui.fxml", listenerManager, breakHandler)
        setLoopDelay(1000, 1500)
    }
}
