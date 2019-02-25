package quantum.api.framework.patterns

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.hybrid.util.calculations.Random
import quantum.api.framework.playersense.QuantumPlayerSense
import java.util.concurrent.TimeUnit

class AntiPatternManager {

    private val patternTypeList = mutableListOf<PatternType>()
    private var performedPattern: PatternType? = DelayAntiPattern()
    private val timer = StopWatch()
    private var interval: Int = Random.nextInt(110, 220)

    init {
        patternTypeList.add(CameraAntiPattern())
        patternTypeList.add(DelayAntiPattern())
        patternTypeList.add(MouseAntiPattern())
        timer.start()
    }

    fun ready(): Boolean {
        return timer.getRuntime(TimeUnit.SECONDS) >= interval
    }

    fun perform(): Boolean {
        return patternTypeList.filter { it != performedPattern }.shuffled().first().also {
            Environment.getBot().logger.fine("[AntiPattern] Executing ${it.javaClass.name}")
            reset()
        }.also {
            performedPattern = it
        }.execute()
    }

    private fun reset(): Boolean {
        timer.reset()
        interval = QuantumPlayerSense.ANTI_PATTERN_INTERVAL.asInteger()
        return true
    }

}