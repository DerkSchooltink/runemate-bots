package bots.premium.woodcutting.data

import quantum.api.game.GameMode
import quantum.api.game.GameMode.OSRS
import quantum.api.game.GameMode.RS3
import quantum.api.game.navigation.BankLocation
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Coordinate
import java.util.*

enum class ChopLocation(
    val map: HashMap<Tree, Coordinate>,
    private val bankName: String,
    val firstBankLocation: BankingLocation,
    vararg val gameMode: GameMode = arrayOf(RS3, OSRS)
) : Locatable {

    PORT_SARIM(
            hashMapOf(Tree.WILLOW to Coordinate(3065, 3258, 0)),
            BankLocation.PORT_SARIM.bankLocationName,
        BankingLocation.PORT_SARIM
    ),

    CORSAIR_COVE(
            hashMapOf(
                    Tree.YEW to Coordinate(2475, 2889, 0),
                    Tree.MAPLE to Coordinate(2480, 2897, 0)
            ),
            BankLocation.CORSAIR_COVE.bankLocationName,
        BankingLocation.CORSAIR_COVE,
            OSRS
    ),

    CASTLE_WARS(
            hashMapOf(Tree.TEAK to Coordinate(2335, 3047, 0)),
            BankLocation.CASTLE_WARS.bankLocationName,
        BankingLocation.CASTLE_WARS
    ),

    TREE_PATCH_VARROCK(
            hashMapOf(
                    Tree.TREE to Coordinate(3237, 3464, 0),
                    Tree.OAK to Coordinate(3237, 3464, 0),
                    Tree.WILLOW to Coordinate(3237, 3464, 0),
                    Tree.YEW to Coordinate(3237, 3464, 0),
                    Tree.MAGIC to Coordinate(3237, 3464, 0),
                    Tree.TEAK to Coordinate(3237, 3464, 0),
                    Tree.MAHOGANY to Coordinate(3237, 3464, 0)
            ),
            "Treepatch Varrock",
        BankingLocation.VARROCK_EAST
    ),

    TREE_PATCH_FALADOR(
            hashMapOf(
                    Tree.TREE to Coordinate(3003, 3372, 0),
                    Tree.OAK to Coordinate(3003, 3372, 0),
                    Tree.WILLOW to Coordinate(3003, 3372, 0),
                    Tree.YEW to Coordinate(3003, 3372, 0),
                    Tree.MAGIC to Coordinate(3003, 3372, 0),
                    Tree.TEAK to Coordinate(3003, 3372, 0),
                    Tree.MAHOGANY to Coordinate(3003, 3372, 0)
            ),
            "Treepatch Falador",
        BankingLocation.FALADOR_EAST
    ),

    CATHERBY(
            hashMapOf(
                    Tree.OAK to Coordinate(2789, 3438, 0),
                    Tree.WILLOW to Coordinate(2783, 3429, 0),
                    Tree.YEW to Coordinate(2759, 3431, 0)
            ),
            BankLocation.CATHERBY.bankLocationName,
        BankingLocation.CATHERBY
    ),

    MENAPHOS(
            hashMapOf(Tree.ACADIA to Coordinate(3187, 2719, 0)),
            BankLocation.MENAPHOS.bankLocationName,
        BankingLocation.MENAPHOS,
            RS3
    ),

    MENAPHOS_VIP(
            hashMapOf(Tree.ACADIA to Coordinate(3181, 2751, 0)),
            BankLocation.MENAPHOS_VIP.bankLocationName,
        BankingLocation.MENAPHOS_VIP,
            RS3
    ),

    SEERS_VILLAGE(
            hashMapOf(
                    Tree.MAPLE to Coordinate(2727, 3500, 0),
                    Tree.YEW to Coordinate(2711, 3463, 0),
                    Tree.MAGIC to Coordinate(2694, 3425, 0),
                    Tree.OAK to Coordinate(2721, 3480, 0)
            ),
            BankLocation.SEERS_VILLAGE.bankLocationName,
        BankingLocation.SEERS_VILLAGE
    ),

    GRAND_EXCHANGE(
            hashMapOf(
                    Tree.OAK to Coordinate(3193, 3460, 0),
                    Tree.YEW to Coordinate(3213, 3503, 0)
            ),
            BankLocation.GRAND_EXCHANGE.bankLocationName,
        BankingLocation.GRAND_EXCHANGE
    ),

    SAWMILL(
            hashMapOf(Tree.YEW to Coordinate(3267, 3482, 0)),
            "Sawmill",
        BankingLocation.VARROCK_EAST
    ),

    FALADOR(
            hashMapOf(Tree.YEW to Coordinate(3031, 3320, 0)),
            "Falador",
        BankingLocation.FALADOR_WEST
    ),

    CLAN_CAMP(
            hashMapOf(Tree.WILLOW to Coordinate(2915, 3299, 0)),
            BankLocation.CLAN_CAMP_FALADOR.bankLocationName,
        BankingLocation.CLAN_CAMP,
            RS3
    ),

    SORCERERS_TOWER(
            hashMapOf(Tree.MAGIC to Coordinate(2702, 3398, 0)),
            "Sorcerer's tower",
        BankingLocation.SEERS_VILLAGE
    ),

    MAGIC_ARENA(
            hashMapOf(Tree.MAGIC to Coordinate(3367, 3310, 0)),
            "Magic arena",
        BankingLocation.DUEL_ARENA
    ),

    RIMMINGTON(
            hashMapOf(Tree.YEW to Coordinate(2938, 3233, 0)),
            "Rimmington",
        BankingLocation.FALADOR_WEST
    ),

    BARBARIAN_OUTPOST(
            hashMapOf(Tree.WILLOW to Coordinate(2520, 3579, 0)),
            BankLocation.BARBARIAN_OUTPOST.bankLocationName,
        BankingLocation.BARBARIAN_OUTPOST
    ),

    SINCLAIRS_MANSION(
            hashMapOf(Tree.MAPLE to Coordinate(2721, 3551, 0)),
            "Sinclair's mansion",
        BankingLocation.SEERS_VILLAGE
    ),

    EDGEVILLE(
            hashMapOf(Tree.YEW to Coordinate(3086, 3471, 0)),
            BankLocation.EDGEVILLE.bankLocationName,
        BankingLocation.EDGEVILLE
    ),

    APE_ATOLL(
            hashMapOf(
                    Tree.MAHOGANY to Coordinate(2718, 2710, 0),
                    Tree.TEAK to Coordinate(2774, 2699, 0)
            ),
            BankLocation.APE_ATOLL_SOUTH.bankLocationName,
        BankingLocation.APE_ATOLL
    ),

    PRIFDINNAS(
            hashMapOf(
                    Tree.YEW to Coordinate(2251, 3377, 1),
                    Tree.MAGIC to Coordinate(2240, 3367, 1)
            ),
            "Prifdinnas",
        BankingLocation.PRIFDINNAS_WOODCUTTING,
            RS3
    ),

    WOODCUTTING_GUILD(
            hashMapOf(
                    Tree.YEW to Coordinate(1593, 3490, 0),
                    Tree.MAGIC to Coordinate(1579, 3488, 0),
                    Tree.REDWOOD to Coordinate(1574, 3482, 1)
            ),
            BankLocation.WOODCUTTING_GUILD.bankLocationName,
        BankingLocation.WOODCUTTING_GUILD,
            OSRS
    ),

    LUMBRIDGE(
            hashMapOf(
                    Tree.TREE to Coordinate(3178, 3226, 0),
                    Tree.OAK to Coordinate(3204, 3244, 0),
                    Tree.YEW to Coordinate(3178, 3226, 0)
            ),
            "Lumbridge",
        BankingLocation.LUMBRIDGE_CASTLE
    ),

    WAIKO(
            hashMapOf(Tree.BAMBOO to Coordinate(1840, 11589, 0)),
            "Waiko",
        BankingLocation.WAIKO_BAMBOO_STORE,
            RS3
    ),

    ELDER_TREES(
            hashMapOf(Tree.ELDER to Coordinate(-1, -1, -1)),
            "Elder trees",
        BankingLocation.GRAND_EXCHANGE,
            RS3
    ),

    DRAYNOR(
            hashMapOf(Tree.WILLOW to Coordinate(3086, 3233, 0)),
            "Draynor",
        BankingLocation.DRAYNOR_VILLAGE
    );

    override fun toString() = bankName
    override fun getPosition() = null
    override fun getHighPrecisionPosition() = null
    override fun getArea() = null
}
