package quantum.api.framework.playersense

import com.runemate.game.api.hybrid.util.calculations.Random

open class GaussianKey(override val key: String, private val min: Double, private val max: Double, override val supplier: () -> Double) :
    QuantumSenseEntry {

    override fun getValue(): Any {
        return Random.nextGaussian(min, max, supplier())
    }
}