package quantum.api.game.combat

import quantum.api.game.combat.CombatStyle.WeaponStyle.Companion.getWeaponStyle
import com.runemate.game.api.hybrid.local.Varbits

enum class CombatStyle(private val styleName: String) {
    UNKNOWN("Unknown"),
    MAGIC("Magic"),
    MELEE("Melee"),
    RANGED("Ranged");

    override fun toString() = styleName

    companion object {
        fun getCombatStyle() = when (getWeaponStyle()) {
            WeaponStyle.MAGIC -> MAGIC
            WeaponStyle.MELEE_CRUSH -> MELEE
            WeaponStyle.MELEE_SLASH -> MELEE
            WeaponStyle.MELEE_STAB -> MELEE
            WeaponStyle.RANGED_THROWN -> RANGED
            WeaponStyle.RANGED_BOLT -> RANGED
            WeaponStyle.RANGED_ARROW -> RANGED
            WeaponStyle.UNKNOWN -> UNKNOWN
        }
    }

    private enum class WeaponStyle(private val styleName: String, private val varbitValue: Int) {
        UNKNOWN("Unknown", -1),
        MAGIC("Magic", 1),
        MELEE_STAB("Melee Stab", 5),
        MELEE_SLASH("Melee Slash", 6),
        MELEE_CRUSH("Melee Crush", 7),
        RANGED_ARROW("Ranged Arrow", 8),
        RANGED_BOLT("Ranged Bolt", 9),
        RANGED_THROWN("Ranged Thrown", 10);

        override fun toString() = styleName

        companion object {
            fun getWeaponStyle() = values().firstOrNull { it.varbitValue == Varbits.load(7618)?.value } ?: UNKNOWN
        }
    }
}