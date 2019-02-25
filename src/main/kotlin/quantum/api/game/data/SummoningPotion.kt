package quantum.api.game.data

import java.util.regex.Pattern

enum class SummoningPotion constructor(private val summoningPotionName: String, private val dose: Int) : Potion {
    SUMMONINGPOTION("Summoning potion", 4),
    SUPERRESTOREPOTION("Super restore", 4),
    SUPERRESTOREFLASK("Super restore flask", 6),
    SUMMONINGFLASK("Summoning flask", 6);

    override val pattern: Pattern = Pattern.compile("$summoningPotionName \\(\\d\\)")

    override fun toString() = "$summoningPotionName ($dose)"
}
