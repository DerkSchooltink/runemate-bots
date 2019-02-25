package bots.premium.woodcutting.data

import quantum.api.game.navigation.BankLocation
import quantum.api.game.navigation.ShopLocation
import com.runemate.game.api.hybrid.entities.details.Locatable

enum class BankingLocation : Locatable {
    PORT_PHASMATYS(BankLocation.PORT_PHASMATYS, Bank.BOOTH),
    LUMBRIDGE_CASTLE(BankLocation.LUMBRIDGE_CASTLE, Bank.BOOTH),
    LUMBRIDGE_COMBAT_ACADEMY(BankLocation.LUMBRIDGE_COMBAT_ACADEMY, Bank.CHEST),
    DRAYNOR_VILLAGE(BankLocation.DRAYNOR_VILLAGE, Bank.NPC),
    BURTHOPE(BankLocation.BURTHORPE, Bank.BOOTH),
    ARDOUGNE_EAST(BankLocation.ARDOUGNE_EAST, Bank.BOOTH),
    PORT_SARIM(BankLocation.PORT_SARIM, Bank.DEPOSIT_BOX),
    VARROCK_EAST(BankLocation.VARROCK_EAST, Bank.BOOTH),
    VARROCK_WEST(BankLocation.VARROCK_WEST, Bank.BOOTH),
    FALADOR_EAST(BankLocation.FALADOR_EAST, Bank.BOOTH),
    FALADOR_WEST(BankLocation.FALADOR_WEST, Bank.BOOTH),
    CATHERBY(BankLocation.CATHERBY, Bank.BOOTH),
    SEERS_VILLAGE(BankLocation.SEERS_VILLAGE, Bank.BOOTH),
    GRAND_EXCHANGE(BankLocation.GRAND_EXCHANGE, Bank.NPC),
    DUEL_ARENA(BankLocation.DUEL_ARENA, Bank.NPC_DUEL_ARENA),
    BARBARIAN_OUTPOST(BankLocation.BARBARIAN_OUTPOST, Bank.CHEST),
    EDGEVILLE(BankLocation.EDGEVILLE, Bank.BOOTH),
    APE_ATOLL(BankLocation.APE_ATOLL_SOUTH, Bank.DEPOSIT_BOX),
    PRIFDINNAS_SOUTH(BankLocation.PRIFDINNAS, Bank.BOOTH),
    PRIFDINNAS_WOODCUTTING(BankLocation.PRIFDDINAS_WOODCUTTING, Bank.CHEST),
    CLAN_CAMP(BankLocation.CLAN_CAMP_FALADOR, Bank.CHEST),
    MENAPHOS(BankLocation.MENAPHOS, Bank.BOOTH),
    MENAPHOS_VIP(BankLocation.MENAPHOS_VIP, Bank.CHEST),
    WAIKO_BAMBOO_STORE(ShopLocation.WAIKO_BAMBOO_STORE),
    WOODCUTTING_GUILD(BankLocation.WOODCUTTING_GUILD, Bank.CHEST),
    CORSAIR_COVE(BankLocation.CORSAIR_COVE, Bank.BOOTH),
    MENAPHOS_IMPERIAL(BankLocation.MENAPHOS_IMPERIAL, Bank.ALL),
    CASTLE_WARS(BankLocation.CASTLE_WARS, Bank.CHEST);

    private val bankName: String
    val banks: Bank?
    val bankLocation: Locatable?
    val action: String

    constructor(location: Locatable, banks: Bank) {
        action = ""
        bankLocation = location
        bankName = bankLocation.toString()
        this.banks = banks
    }

    constructor(location: Locatable) {
        val shopLocation = location as ShopLocation
        bankName = shopLocation.toString()
        banks = null
        bankLocation = location
        action = shopLocation.action
    }

    override fun toString() = bankName
    override fun getPosition() = bankLocation?.position
    override fun getHighPrecisionPosition() = bankLocation?.highPrecisionPosition
    override fun getArea() = bankLocation?.area

    enum class Bank constructor(vararg val bankName: String) {
        ALL("Bank booth", "Fadli", "Bank chest", "Bank chest", "Bank deposit box", "Deposit chest", "Banker"),
        BOOTH("Bank booth"),
        NPC_DUEL_ARENA("Fadli"),
        CHEST("Bank chest"),
        DEPOSIT_BOX("Bank chest", "Bank deposit box", "Deposit chest"),
        NPC("Banker");
    }
}
