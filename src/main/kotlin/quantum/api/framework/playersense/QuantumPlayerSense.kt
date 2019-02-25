package quantum.api.framework.playersense

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.player_sense.PlayerSense
import com.runemate.game.api.hybrid.util.calculations.Random
import java.util.*

enum class QuantumPlayerSense(private var entry: QuantumSenseEntry) {

    ANTI_PATTERN_INTERVAL(GaussianKey("quantum_anti_pattern_interval", 110.0, 200.0) { Random.nextDouble(130.0, 180.0) }),
    AVERAGE_IDLE_TIME(GaussianKey("quantum_average_idle_time", 300.0, 5000.0) { Random.nextDouble(500.0, 4800.0) }),
    AVERAGE_DISTANCE_TO_ENTITY(GaussianKey("quantum_average_distance_to_entity", 7.0, 13.0) { Random.nextDouble(8.0, 12.0) }),
    EMERGENCY_HEALTH_AMOUNT(GaussianKey("quantum_minimum_health_percentage", 20.0, 30.0) { Random.nextDouble(21.0, 29.0) }),
    SPAM_CLICK_COUNT(GaussianKey("quantum_spam_click_amount", 1.0, 3.0) { Random.nextDouble(1.0, 2.0) }),
    ALMOST_EMPTY_INVENTORY_SLOTS(GaussianKey("quantum_almost_empty_inventory", 3.0, 6.0) { Random.nextDouble(4.0, 5.0) });

    companion object {
        fun initialize() {
            Arrays.stream(values()).forEach {
                var value: Any? = PlayerSense.get(it.key())
                if (value == null) {
                    value = it.entry.getValue()
                    PlayerSense.put(it.key(), value)
                }
                Environment.getLogger().debug("[PlayerSense] key=${it.key()} value=$value")
            }
        }

        fun clear() = Arrays.stream(values()).map(QuantumPlayerSense::key).forEach(PlayerSense::clear)
    }

    fun key() = entry.key

    fun asInteger() = entry.getValue().let {
        if (it is Number) it.toInt() else 0
    }

    fun asDouble() = entry.getValue().let {
        if (it is Number) it.toDouble() else 0.0
    }

    fun asLong() = entry.getValue().let {
        if (it is Number) it.toLong() else 0
    }

    fun asBoolean() = entry.getValue().let {
        if (it is Boolean) it else false
    }
}