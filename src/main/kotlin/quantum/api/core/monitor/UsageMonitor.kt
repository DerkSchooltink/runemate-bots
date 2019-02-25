package quantum.api.core.monitor

import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.logger.BotLogger
import java.util.*
import java.util.concurrent.TimeUnit


class UsageMonitor(private val bot: AbstractBot, usageLimit: Long, timeUnit: TimeUnit) {

    val stopwatch = StopWatch()

    private val botName = bot.metaData.name
    private val usageLimit: Long = timeUnit.toMillis(usageLimit)
    private var lastUpdate: Long = 0
    private val whiteList = listOf("Auxi", "Gengsta", "Derk", "Noah", "Aidden", "RobinPC")

    init {
        stopwatch.start()
        Environment.getForumName().let { if (whiteList.contains(it)) bot.logger.debug("Congratulations, $it is on the whitelist of this bot!") }
    }

    fun checkUsage() {
        if (bot.metaData.hourlyPrice.toDouble() == 0.0 && !whiteList.contains(Environment.getForumName())) {
            val usageDay = bot.settings.getProperty("${botName}_bot_usage_day", "0")
            if (usageDay == null) {
                bot.logger.info("day was null")
            } else {
                val millis = updateUsage()
                val currentDay = Calendar.getInstance().timeInMillis / TimeUnit.DAYS.toMillis(1)
                if (java.lang.Long.parseLong(usageDay) != currentDay) {
                    bot.logger.debug("[Usage] Resetting limit")
                    bot.settings.setProperty("${botName}_bot_usage_day", java.lang.Long.toString(currentDay))
                    bot.settings.setProperty("${botName}_bot_usage", "0")
                }
                if (millis >= usageLimit) {
                    ClientUI.showAlert(BotLogger.Level.WARN, "The daily usage limit of 2 hours has been reached. Please consider using the premium version.")
                    bot.stop("The daily usage limit of 2 hours has been reached. Please consider using the premium version.")
                } else {
                    bot.logger.debug("[Usage] Usage is under limit")
                    bot.logger.debug("[Usage] " + String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millis),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                    ))
                }
            }
        }
    }

    private fun updateUsage(): Long {
        val timeWritten = java.lang.Long.parseLong(bot.settings.getProperty("${botName}_bot_usage", "0"))
        val botRuntime = stopwatch.runtime
        val meanTime = botRuntime - lastUpdate
        val total = timeWritten + meanTime
        lastUpdate = botRuntime
        bot.settings.setProperty("${botName}_bot_usage", total.toString())
        return total
    }
}