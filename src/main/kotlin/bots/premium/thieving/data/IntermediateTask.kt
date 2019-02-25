package bots.premium.thieving.data

import com.runemate.game.api.hybrid.location.Coordinate

enum class IntermediateTask(private val taskName: String, val gameObject: String, val action: String, val location: Coordinate) {
    IVY("Ivy chopping", "Ivy", "Chop", Coordinate(2241, 3378, 1));

    override fun toString() = taskName
}