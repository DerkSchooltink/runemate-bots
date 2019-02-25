package bots.private_bots.catacombs.data

import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

enum class CatacombsMonster constructor(private val monsterName: String, val coordinate: Coordinate, val deathAnimation: Int) : Locatable {
    HILL_GIANT("Hill Giant", Coordinate(1665, 10072, 0), 4653),
    DUST_DEVIL("Dust devil", Coordinate(1715, 10031, 0), 1558),
    ANKOU("Ankou", Coordinate(1641, 9995, 0), 836),
    DAGANNOTH("Dagannoth", Coordinate(1668, 10003, 0), 1342),
    LESSER_DEMON("Lesser demon", Coordinate(1718, 10069, 0), 67),
    MUTATED_BLOODVELD("Mutated Bloodveld", Coordinate(1680, 10073, 0), 1553),
    NECHRYAEL("Greater Nechryael", Coordinate(1691, 10081, 0), 1530),
    CYCLOPS("Cyclops", Coordinate(1653, 10022, 0), 4653),
    HELLHOUND("Hellhound", Coordinate(1646, 10069, 0), 6576);

    override fun toString() = monsterName

    override fun getHighPrecisionPosition() = Coordinate.HighPrecision(coordinate.x, coordinate.y, coordinate.plane)
    override fun getArea(): Area.Rectangular = coordinate.area
    override fun getPosition() = coordinate
}
