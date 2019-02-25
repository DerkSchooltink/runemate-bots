package quantum.api.game.data

import java.util.regex.Pattern

enum class RangingPotion constructor(private val rangingPotionName: String, private val dose: Int) : Potion {
    EXTREME_RANGING("Extreme ranging", 4),
    SUPER_RANGING("Super ranging potion", 4),
    SUPREME_RANGING("Supreme ranging potion", 6),
    GRAND_RANGING("Grand ranging potion", 6),
    EXTREME_SHARPSHOOTER("Extreme sharpshooter's potion", 6);

    override val pattern: Pattern = Pattern.compile("$rangingPotionName \\(\\d\\)")

    override fun toString() = "$rangingPotionName ($dose)"
}
