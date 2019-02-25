package bots.private_bots.catacombs

import bots.private_bots.catacombs.settings.CatacombsSettings
import bots.private_bots.catacombs.ui.CatacombsSlayerUI
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class CatacombsSlayer : ExtendedTaskBot() {

    private val listenerManager = ListenerManager()
    private val breakHandler = BreakManager()

    val botSettings = CatacombsSettings()

    override fun onStart(vararg args: String) {
        QuantumPlayerSense.initialize()
        setLoopDelay(600, 1200)
        embeddableUI = UIExtender(this, CatacombsSlayerUI(this), "com/bots/private_bots/catacombs/ui/catacombs_ui.fxml", listenerManager, breakHandler)
    }
}
