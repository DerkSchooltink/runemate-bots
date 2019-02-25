package quantum.api.game.navigation

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

enum class BankLocation constructor(val bankLocationName: String, val type: Type, private val rs3Coordinate: Coordinate, private val osrsCoordinate: Coordinate) : Locatable {
    NONE("None", Type.NONE, Coordinate(0, 0, 0), Coordinate(0, 0, 0)),
    APE_ATOLL_SOUTH("Ape Atoll", Type.DEPOSIT, Coordinate(3047, 3237, 0), Coordinate(3047, 3237, 0)),
    ARDOUGNE_EAST("Ardougne East", Type.FULL, Coordinate(2655, 3283, 0), Coordinate(2655, 3283, 0)),
    BARBARIAN_OUTPOST("Barbarian outpost", Type.FULL, Coordinate(2535, 3572, 0), Coordinate(2535, 3572, 0)),
    BURGH_DE_ROTT("Burgh de Rott", Type.FULL, Coordinate(3494, 3211, 0), Coordinate(3494, 3211, 0)),
    BURTHORPE("Burthorpe", Type.FULL, Coordinate(2888, 3535, 0), Coordinate(0, 0, 0)),
    CASTLE_WARS("Castle wars", Type.FULL, Coordinate(-1, -1, -1), Coordinate(2443, 3083, 0)),
    CATHERBY("Catherby", Type.FULL, Coordinate(2795, 3439, 0), Coordinate(2809, 3440, 0)),
    CLAN_CAMP_FALADOR("Clan camp Falador", Type.FULL, Coordinate(3011, 3355, 0), Coordinate(-1, -1, 0)),
    CORSAIR_COVE("Corsair cove", Type.FULL, Coordinate(-1, -1, -1), Coordinate(2569, 2864, 0)),
    DRAYNOR_VILLAGE("Draynor Village", Type.FULL, Coordinate(3091, 3244, 0), Coordinate(3093, 3243, 0)),
    DUEL_ARENA("Duel Arena", Type.SPECIAL, Coordinate(3349, 3238, 0), Coordinate(3349, 3238, 0)),
    EDGEVILLE("Edgeville", Type.FULL, Coordinate(3094, 3496, 0), Coordinate(3094, 3491, 0)),
    FALADOR_EAST("Falador East", Type.FULL, Coordinate(3009, 3355, 0), Coordinate(3009, 3355, 0)),
    FALADOR_WEST("Falador West", Type.FULL, Coordinate(2945, 3369, 0), Coordinate(0, 0, 0)),
    FISHING_GUILD("Fishing Guild", Type.FULL, Coordinate(2584, 3422, 0), Coordinate(2587, 3419, 0)),
    GRAND_EXCHANGE("Grand Exchange", Type.FULL, Coordinate(3180, 3482, 0), Coordinate(3165, 3487, 0)),
    KARAMJA("Stiles", Type.SPECIAL, Coordinate(2851, 3143, 0), Coordinate(3045, 3234, 0)),
    LIVING_ROCK_CAVERNS("Living Rock Caverns", Type.SPECIAL, Coordinate(3652, 5114, 0), Coordinate(0, 0, 0)),
    LUMBRIDGE_CASTLE("Lumbridge Castle", Type.FULL, Coordinate(3208, 3219, 2), Coordinate(3208, 3219, 2)),
    LUMBRIDGE_COMBAT_ACADEMY("Lumbridge Combat Academy", Type.FULL, Coordinate(3215, 3257, 0), Coordinate(0, 0, 0)),
    PISCARILIUS("Piscarilius (Zeah)", Type.FULL, Coordinate(0, 0, 0), Coordinate(1804, 3790, 0)),
    PISCATORIS("Piscatoris", Type.FULL, Coordinate(2330, 3690, 0), Coordinate(2330, 3690, 0)),
    PORT_PHASMATYS("Port Phasmatys", Type.FULL, Coordinate(0, 0, 0), Coordinate(3690, 3466, 0)),
    PORT_SARIM("Port Sarim", Type.DEPOSIT, Coordinate(3045, 3234, 0), Coordinate(3045, 3234, 0)),
    PRIFDDINAS_WATERFALL("Prifddinas Waterfall", Type.FULL, Coordinate(2293, 3404, 2), Coordinate(0, 0, 0)),
    PRIFDDINAS_WOODCUTTING("Prifdinnas woodcutting area", Type.FULL, Coordinate(2237, 3385, 1), Coordinate(2237, 3385, 1)),
    SEERS_VILLAGE("Seer's Village", Type.FULL, Coordinate(2727, 3494, 0), Coordinate(2725, 3492, 0)),
    SHILO_VILLAGE("Shilo Village", Type.FULL, Coordinate(2853, 2955, 0), Coordinate(2853, 2955, 0)),
    VARROCK_EAST("Varrock East", Type.FULL, Coordinate(0, 0, 0), Coordinate(3253, 3420, 0)),
    VARROCK_WEST("Varrock West", Type.FULL, Coordinate(3190, 3435, 0), Coordinate(0, 0, 0)),
    WAIKO("Waiko (The Arc)", Type.FULL, Coordinate(1831, 11613, 0), Coordinate(0, 0, 0)),
    MENAPHOS("Menaphos", Type.FULL, Coordinate(3235, 2759, 0), Coordinate(-1, -1, 0)),
    MENAPHOS_VIP("Menaphos VIP", Type.FULL, Coordinate(3182, 2742, 0), Coordinate(-1, -1, 0)),
    WHALES_MAW("Whale's Maw (The Arc)", Type.DEPOSIT, Coordinate(2058, 11781, 0), Coordinate(0, 0, 0)),
    WOODCUTTING_GUILD("Woodcutting Guild", Type.FULL, Coordinate(-1, -1, 0), Coordinate(1592, 3476, 0)),
    PRIFDINNAS("Prifdinnas", Type.FULL, Coordinate(2203, 3368, 1), Coordinate(-1, -1, 0)),
    KOUREND_ARCEUS("Kourend Arceus district", Type.FULL, Coordinate(-1, -1, -1), Coordinate(1635, 3740, 0)),
    MENAPHOS_IMPERIAL("Menaphos imperial", Type.FULL, Coordinate(3173, 2705, 0), Coordinate(-1, -1, 0));

    override fun toString() = bankLocationName

    override fun getPosition() = if (Environment.isOSRS()) osrsCoordinate else rs3Coordinate
    override fun getArea() = Area.Rectangular(position)
    override fun getHighPrecisionPosition(): Coordinate.HighPrecision = position.highPrecisionPosition

    enum class Type {
        NONE, DEPOSIT, FULL, SPECIAL
    }
}
