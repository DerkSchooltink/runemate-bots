package quantum.api.game.data

import java.util.regex.Pattern

enum class Overload constructor(private val overloadName: String, private val dose: Int) : Potion {
    OVERLOAD("Overload", 4),
    OVERLOAD_FLASK("Overload flask", 6),
    HOLY_OVERLOAD("Holy overload potion", 6);

    override val pattern: Pattern = Pattern.compile("$overloadName \\(\\d\\)")

    override fun toString() = "$overloadName ($dose)"
}
