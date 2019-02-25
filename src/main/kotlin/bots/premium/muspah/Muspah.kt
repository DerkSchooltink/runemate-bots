package bots.premium.muspah

import bots.premium.muspah.settings.MuspahSettings
import bots.premium.muspah.ui.MuspahController
import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.script.framework.core.LoopingThread
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.core.monitor.UsageMonitor
import quantum.api.framework.playersense.QuantumPlayerSense
import java.util.concurrent.TimeUnit

class Muspah : ExtendedTaskBot() {

    private lateinit var usageMonitor: UsageMonitor

    val settings = MuspahSettings()

    override fun onStart(vararg arguments: String) {
        QuantumPlayerSense.initialize()

        embeddableUI = UIExtender(this, MuspahController(this), "com/bots/premium/muspah/ui/muspah_ui.fxml")
        usageMonitor = UsageMonitor(this, 2L, TimeUnit.HOURS)

        if (metaData.hourlyPrice.toDouble() == 0.0) {
            LoopingThread({ usageMonitor.checkUsage() }, 10000).start()
            ClientUI.showAlert("This is the lite version of Quantum Muspah, good for 2 hours of free usage per day. If you want to use more hours, switch to the premium bot!")
        }

        if (!usageMonitor.stopwatch.isRunning) usageMonitor.stopwatch.start()
    }

    override fun onStop() {
        if (usageMonitor.stopwatch.isRunning) usageMonitor.stopwatch.stop()
    }

    override fun onResume() {
        if (!usageMonitor.stopwatch.isRunning) usageMonitor.stopwatch.start()
    }

    override fun onPause() {
        if (usageMonitor.stopwatch.isRunning) usageMonitor.stopwatch.stop()
    }
}