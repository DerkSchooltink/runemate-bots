package bots.free.summoning.data

import com.runemate.game.api.hybrid.location.Coordinate

enum class Obelisk(private val obeliskName: String, val obeliskLocation: Coordinate, val bankLocation: Coordinate) {
    MENAPHOS("Menaphos obelisk", Coordinate(3234, 2798, 0), Coordinate(3234, 2759, 0)),
    TAVERLY("Taverly obelisk", Coordinate(2931, 3448, 0), Coordinate(2875, 3418, 0)),
    PRIFDDINAS("Prifddinas obelisk", Coordinate(2139, 3372, 1), Coordinate(2153, 3340, 1));

    override fun toString() = obeliskName
}