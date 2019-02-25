package quantum.api.framework.patterns

import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.script.Execution

class DelayAntiPattern : PatternType {

    override fun execute(): Boolean {
        Environment.getBot().logger.info("Performing AntiPattern {DAP}")
        return Execution.delay((QuantumPlayerSense.AVERAGE_IDLE_TIME.asLong() * Random.nextDouble(0.75, 1.25)).toLong())
    }
}