package bots.premium.divination

import bots.premium.divination.settings.HallOfMemoriesSettings
import bots.premium.divination.ui.HallOfMemoriesController
import quantum.api.core.ExtendedTaskBot
import quantum.api.core.monitor.UsageMonitor
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.script.framework.core.LoopingThread
import java.util.concurrent.TimeUnit

class HallOfMemories : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val settings = HallOfMemoriesSettings()

    private lateinit var usageMonitor: UsageMonitor

    override fun onStart(vararg arguments: String?) {
        embeddableUI = UIExtender(this, HallOfMemoriesController(this), "com/bots/premium/divination/ui/hall_of_memories_ui.fxml", listenerManager, breakHandler)
        usageMonitor = UsageMonitor(this, 1L, TimeUnit.HOURS)

        if (metaData.hourlyPrice.toDouble() == 0.0) {
            LoopingThread(Runnable { usageMonitor.checkUsage() }, 10000).start()
            ClientUI.showAlert("This is the lite version of Quantum Hall of Memories, good for 1 hour of free usage per day. If you want to use more hours, switch to the premium bot!")
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