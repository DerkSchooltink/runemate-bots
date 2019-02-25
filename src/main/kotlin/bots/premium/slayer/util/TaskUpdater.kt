package bots.premium.slayer.util

import bots.premium.slayer.frame.SlayerTask
import bots.premium.slayer.settings.SlayerSettings
import com.runemate.game.api.script.framework.core.LoopingThread

class TaskUpdater(private val slayerSettings: SlayerSettings) {

    fun initialize() {
        LoopingThread({
            updateSlayerTask(slayerSettings)
        }, 2000).start()
    }

    private fun updateSlayerTask(slayerSettings: SlayerSettings) = SlayerTask.updateTask(slayerSettings)
}