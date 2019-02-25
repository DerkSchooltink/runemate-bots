package bots.premium.slayer

import bots.premium.slayer.settings.SlayerSettings
import bots.premium.slayer.ui.SlayerUI
import bots.premium.slayer.util.LootUpdater
import bots.premium.slayer.util.TaskUpdater
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class Slayer : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val slayerSettings = SlayerSettings()
    val lootUpdater = LootUpdater(this)

    override fun onStart(vararg strings: String) {
        embeddableUI = UIExtender(this, SlayerUI(this), "com/bots/premium/slayer/ui/slayer_ui.fxml", listenerManager, breakHandler)
        setLoopDelay(600, 1200)

        lootUpdater.initialize()
        TaskUpdater(slayerSettings).initialize()
        QuantumPlayerSense.initialize()
    }
}
