package quantum.api.game.navigation

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

enum class ShopLocation constructor(private val shopLocationName: String, val entityName: String, private val rs3Coordinate: Coordinate, private val osrsCoordinate: Coordinate, val action: String) : Locatable {

    WAIKO_BAMBOO_STORE("Waiko bamboo store", "Zhuka (bamboo merchant)", Coordinate(1827, 11594, 0), Coordinate(-1, -1, 0), "TradeRequest");

    override fun toString() = shopLocationName

    override fun getPosition() = if (Environment.isOSRS()) osrsCoordinate else rs3Coordinate
    override fun getArea() = Area.Rectangular(position)
    override fun getHighPrecisionPosition(): Coordinate.HighPrecision = position.highPrecisionPosition
}
