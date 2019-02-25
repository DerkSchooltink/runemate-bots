package bots.premium.slayer.frame

import bots.premium.slayer.data.SlayerCombatStyle
import bots.premium.slayer.data.SpecialItem
import bots.premium.slayer.data.SpecialItemGroup
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment

class Requirements(val styleSlayer: SlayerCombatStyle) {

    val specialItemsAsStringArray: MutableList<String> = mutableListOf()

    constructor(styleSlayer: SlayerCombatStyle, vararg items: SpecialItem) : this(styleSlayer) {
        items.map { specialItemsAsStringArray.add(it.specialItemName) }
    }

    constructor(styleSlayer: SlayerCombatStyle, specialItemGroup: SpecialItemGroup) : this(styleSlayer) {
        specialItemGroup.let { group -> specialItemsAsStringArray.addAll(group.specialItems.map { it.specialItemName }) }
    }

    fun hasEquipment() = specialItemsAsStringArray.let { it.isEmpty() || isWearing(it.toList()) }

    private fun isWearing(items: List<String>?) = items?.any { specialItem -> Equipment.newQuery().names(specialItem).results().isEmpty() } == true
}
