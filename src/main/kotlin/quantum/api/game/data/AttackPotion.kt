package quantum.api.game.data

import java.util.regex.Pattern

enum class AttackPotion(private val attackPotionName: String, private val dose: Int) : Potion {
    ATTACK("Attack potion", 4),
    SUPER_ATTACK("Super attack", 4);

    override val pattern: Pattern = Pattern.compile("$attackPotionName \\(\\d\\)")

    override fun toString() = "$attackPotionName ($dose)"
}