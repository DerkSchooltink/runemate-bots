package quantum.api.game.data

import java.util.regex.Pattern

enum class StrengthPotion(private val strengthPotionName: String, private val dose: Int) : Potion {
    STRENGTH("Strength potion", 4),
    SUPER_STRENGTH("Super strenth", 4);

    override val pattern: Pattern = Pattern.compile("$strengthPotionName \\(\\d\\)")

    override fun toString() = "$strengthPotionName ($dose)"
}