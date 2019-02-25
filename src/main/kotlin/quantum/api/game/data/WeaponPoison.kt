package quantum.api.game.data

import com.runemate.game.api.hybrid.local.Varbits
import java.util.regex.Pattern

enum class WeaponPoison constructor(private val weaponPoisonName: String, varBit: Int, private val dose: Int) : Potion {
    WEAPON_POISON_PLUS_PLUS("Weapon poison++", 2102, 4),
    WEAPON_POISION_PLUS_PLUS_FLASK("Weapon poison++ flask", 2102, 6);

    override val pattern: Pattern = Pattern.compile(weaponPoisonName.replace("poison++", "poison\\+\\+") + " \\(\\d\\)", Pattern.CASE_INSENSITIVE)

    val varbit = Varbits.load(varBit)

    override fun toString() = "$weaponPoisonName ($dose)"
}
