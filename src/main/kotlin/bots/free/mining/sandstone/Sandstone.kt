package bots.free.mining.sandstone

import bots.free.mining.sandstone.settings.BotSettings
import bots.free.mining.sandstone.ui.SandstoneUI
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense

class Sandstone : ExtendedTaskBot() {

    val botSettings = BotSettings()

    override fun onStart(vararg args: String) {
        QuantumPlayerSense.initialize()
        embeddableUI = UIExtender(this, SandstoneUI(this), "com/bots/free/mining/sandstone/ui/sandstone_ui.fxml")
        setLoopDelay(1000, 1500)
    }
}
