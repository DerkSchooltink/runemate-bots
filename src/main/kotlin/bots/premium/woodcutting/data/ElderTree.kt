package bots.premium.woodcutting.data

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.util.Timer

data class ElderTree(private val location: Coordinate, private var isAvailable: Boolean) : Locatable {

    private var timer: Timer = Timer(600000L)

    fun isAvailable(): Boolean {
        timer.let {
            if (!it.isRunning || it.elapsedTime == 0L) {
                it.stop()
                isAvailable = true
            }
        }
        return isAvailable
    }

    fun setAvailable(available: Boolean) {
        if (!available) {
            timer.start()
            Environment.getLogger().debug("Starting timer for tree on $location")
        }
        isAvailable = available
    }

    override fun getPosition() = location
    override fun getHighPrecisionPosition() = Coordinate.HighPrecision(location.x, location.y, location.plane)
    override fun getArea() = Area.Rectangular(location)
}
