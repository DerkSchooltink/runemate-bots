package bots.premium.woodcutting.data

import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.util.calculations.Random

enum class ElderTreeLocation constructor(private val elderTreeLocationName: String, val elderTree: ElderTree) : Locatable {

    SORCERERS_TOWER("Sorcerer's tower", ElderTree(Coordinate(2739, 3401, 0), true)),
    SOUTH_YANILLE("Yanille", ElderTree(Coordinate(2571, 3062, 0), true)),
    TREE_GNOME_STRONGHOLD("Tree gnome stronghold", ElderTree(Coordinate(2430, 3452, 0), true)),
    SOUTH_DRAYNOR("Draynor", ElderTree(Coordinate(3100, 3217, 0), true)),
    SOUTH_FALADOR("Falador", ElderTree(Coordinate(3056, 3317, 0), true)),
    SOUTH_VARROCK("South Varrock", ElderTree(Coordinate(3259, 3372, 0), true)),
    LLETYA("Lletya", ElderTree(Coordinate(2295, 3147, 0), true)),
    PISCATORIS("Piscatoris", ElderTree(Coordinate(2326, 3603, 0), true)),
    EDGEVILLE("Edgeville", ElderTree(Coordinate(3090, 3456, 0), true)),
    MELZARS_MAZE("Melzar's maze", ElderTree(Coordinate(2934, 3228, 0), true));

    override fun getHighPrecisionPosition() = elderTree.highPrecisionPosition

    override fun getArea(): Area.Rectangular = elderTree.area

    override fun getPosition() = elderTree.position

    override fun toString() = elderTreeLocationName

    companion object {
        fun suitableLocations(): Array<ElderTreeLocation> {
            return arrayOf(SORCERERS_TOWER, SOUTH_DRAYNOR, SOUTH_DRAYNOR, SOUTH_FALADOR, SOUTH_VARROCK, EDGEVILLE, MELZARS_MAZE)
        }

        fun randomSuitableLocation(): ElderTreeLocation {
            return suitableLocations()[Random.nextInt(0, suitableLocations().size - 1)]
        }
    }
}
