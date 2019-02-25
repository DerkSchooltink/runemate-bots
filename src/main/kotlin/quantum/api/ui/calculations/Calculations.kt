package quantum.api.ui.calculations

import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.hybrid.util.Time
import com.runemate.game.api.hybrid.util.calculations.CommonMath
import java.util.concurrent.TimeUnit


object Calculations {

    fun hourlyRate(i: Int, timer: StopWatch): Int {
        return CommonMath.rate(TimeUnit.HOURS, timer.runtime, i.toDouble()).toInt()
    }

    fun timeToLevel(xpGain: Long, xpToLevel: Long, timer: StopWatch): String {
        val xpRate: Double = formatExpRateMillis(xpGain, timer)
        if (xpRate > 0 && xpToLevel > 0) {
            return Time.format((xpToLevel / xpRate).toLong())
        }
        return "Unknown"
    }

    fun formatTimeUnit(millis: Long) = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
    )

    private fun formatExpRateMillis(i: Long, timer: StopWatch): Double {
        return CommonMath.rate(TimeUnit.MILLISECONDS, timer.runtime, i.toDouble())
    }
}