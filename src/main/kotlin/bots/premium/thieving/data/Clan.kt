package bots.premium.thieving.data

import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.Varbits
import com.runemate.game.api.hybrid.location.Coordinate

enum class Clan(val workerName: String, private val requirement: Int, val coordinate: Coordinate, val varp: Int) {
    MEILYR("Meilyr worker", 98, Coordinate(2230, 3398, 1), 25224),
    CRWYS("Crwys worker", 97, Coordinate(2248, 3380, 1), 25218),
    HEFIN("Hefin worker", 96, Coordinate(2187, 3400, 1), 25223),
    TRAHAEARN("Trahaearn worker", 95, Coordinate(2231, 3301, 1), 25220),
    CADARN("Cadarn worker", 93, Coordinate(2269, 3347, 1), 25219),
    AMLODD("Amlodd worker", 94, Coordinate(2142, 3379, 1), 25222),
    ITHELL("Ithell worker", 92, Coordinate(2144, 3339, 1), 25221),
    IORWERTH("Iorwerth worker", 91, Coordinate(2186, 3301, 1), 25217);

    fun isThievable() = Varbits.load(varp)?.run { value < 3 } ?: false
    fun isRequirementMet() = Skill.THIEVING.currentLevel >= requirement
}