package bots.premium.slayer.data

import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.hud.interfaces.NpcContact
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.Players

enum class SlayerMaster(val contact: NpcContact.Contact, val masterName: String, val location: Coordinate, private val slayerLevel: Int, private val combatLevel: Int) : Locatable {
    TURAEL(NpcContact.RS3.SPRIA, "Turael", Coordinate(2910, 3423, 0), 0, 0),
    SPRIA(NpcContact.RS3.SPRIA, "Spria", Coordinate(2910, 3423, 0), 0, 0),
    MAZCHNA(NpcContact.RS3.MAZCHNA, "Mazchna", Coordinate(3510, 3509, 0), 0, 20),
    VANNAKA(NpcContact.RS3.VANNAKA, "Vannaka", Coordinate(3146, 9915, 0), 0, 40),
    CHAELDAR(NpcContact.RS3.CHAELDAR, "Chaeldar", Coordinate(0, 0, 0), 0, 75),
    SUMONA(NpcContact.RS3.SUMONA, "Sumona", Coordinate(3358, 2994, 0), 35, 90),
    DURADEL(NpcContact.RS3.DURADEL, "Duradel", Coordinate(2869, 2982, 1), 50, 100),
    LAPALOK(NpcContact.RS3.LAPALOK, "Lapalok", Coordinate(2869, 2982, 1), 50, 100),
    KURADAL(NpcContact.RS3.KURADAL, "Kuradal", Coordinate(1688, 5287, 1), 75, 110),
    MORVRAN(NpcContact.RS3.MOVRAN, "Morvran", Coordinate(2195, 3327, 1), 85, 120);

    fun canUse(): Boolean {
        var canUse = true
        val player = Players.getLocal()
        if (!RuneScape.isLoggedIn() || player == null) {
            ClientUI.showAlert("Please log in your account before setting up your bot!")
            canUse = false
        } else if (player.combatLevel < combatLevel) {
            ClientUI.showAlert("Your combat level isn't high enough to use this master!")
            canUse = false
        } else if (Skill.SLAYER.baseLevel < slayerLevel) {
            ClientUI.showAlert("Your slayer level isn't high enough to use this master!")
            canUse = false
        }
        return canUse
    }

    override fun toString() = masterName

    fun names() = SlayerMaster.values().map { it.masterName }

    override fun getHighPrecisionPosition() = Coordinate.HighPrecision(location.x, location.y, location.plane)

    override fun getArea(): Area.Rectangular = location.area

    override fun getPosition() = location
}
