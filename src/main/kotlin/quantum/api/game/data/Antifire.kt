package quantum.api.game.data

import com.runemate.game.api.hybrid.local.Varbits
import java.util.regex.Pattern

enum class Antifire constructor(private val antifireName: String, private val varBit: Int, private val dose: Int) :
    Potion {
    BRIGHTFIRE("Brightfire potion", 498, 6),
    SUPERANTIFIRE("Super antifire", 498, 4),
    ANTIFIRE("Antifire", 497, 4),
    ANTIFIREMIX("Antifire mix", 497, 2),
    ANTIFIREFLASK("Antifire flask", 497, 6),
    SEARING_ANTIFIRE("Searing overload potion", 498, 6),
    SUPERANTIFIREFLASK("Super antifire flask", 498, 6);

    override val pattern: Pattern = Pattern.compile("$antifireName \\(\\d\\)")

    fun getVarbit() = Varbits.load(varBit)
    fun isDosed() = getVarbit()?.value != 0

    override fun toString() = "$antifireName ($dose)"
}
