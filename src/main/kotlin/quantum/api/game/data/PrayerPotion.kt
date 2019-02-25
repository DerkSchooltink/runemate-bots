package quantum.api.game.data

import java.util.regex.Pattern

enum class PrayerPotion(private val prayerPotionName: String, private val dose: Int) : Potion {
    PRAYERFLASK("Prayer flask", 6),
    PRAYERPOTION("Prayer potion", 4),
    SUPERPRAYERPOTION("Super prayer", 4),
    SUPERPRAYERFLASK("Super prayer flask", 6),
    SUPERPRAYERRENEWAL("Super prayer renewal potion", 4);

    override val pattern: Pattern = Pattern.compile("$prayerPotionName \\(\\d\\)")

    override fun toString() = "$prayerPotionName ($dose)"
}
