package bots.premium.slayer

import bots.premium.slayer.settings.SlayerSettings
import bots.premium.slayer.ui.SlayerUI
import bots.premium.slayer.util.LootUpdater
import bots.premium.slayer.util.TaskUpdater
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense

class Slayer : ExtendedTaskBot() {

    val slayerSettings = SlayerSettings()
    val lootUpdater = LootUpdater(this)

    override fun onStart(vararg strings: String) {
        embeddableUI = UIExtender(this, SlayerUI(this), "com/bots/premium/slayer/ui/slayer_ui.fxml")
        setLoopDelay(600, 1200)

        lootUpdater.initialize()
        TaskUpdater(slayerSettings).initialize()
        QuantumPlayerSense.initialize()
    }
}
