package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.data.Monster
import quantum.api.core.ExtendedTask
import com.runemate.game.api.client.ClientUI

class UnsupportedTaskHandler : ExtendedTask<Slayer>() {

    override fun validate() = Monster.unsupportedMonsters.contains(bot.slayerSettings.task?.monster) && bot.slayerSettings.task?.amount != 0

    override fun execute() {
        if (bot.slayerSettings.task != null) {
            logger.warn("Task not supported! Either manually do the task or cancel it.")
            ClientUI.sendTrayNotification("Task not supported! Either manually do the task or cancel it.")
            ClientUI.showAlert("Task not supported! Either manually do the task or cancel it.")

            bot.slayerSettings.task?.let {
                logger.warn(it.names)
                logger.warn(it.monster)
            }

            bot.pause()
        }
    }
}
