package quantum.api.game.data

import java.util.regex.Pattern

interface Potion {
    override fun toString(): String
    val pattern: Pattern
}