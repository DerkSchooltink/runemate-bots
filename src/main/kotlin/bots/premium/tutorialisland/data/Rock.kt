package bots.premium.tutorialisland.data

import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.util.collections.Pair
import java.awt.Color

enum class Rock constructor(private val color: Pair<Color, Color>) {
    TIN(Pair(Color(48, 48, 48), Color(106, 106, 106))),
    COPPER(Pair(Color(48, 48, 48), Color(60, 44, 34)));

    fun getClosestWithOre(): GameObject? = GameObjects.newQuery().names("Rocks").colorSubstitutions(color).results().nearest()
}