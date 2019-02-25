package quantum.api.framework.extensions

import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

fun areaOf(vararg coordinate: Coordinate): Area.Absolute = Area.absolute(coordinate.toList())
fun circularAreaOf(center: Coordinate, radius: Double): Area.Circular = Area.circular(center, radius)