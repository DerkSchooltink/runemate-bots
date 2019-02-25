package bots.premium.muspah.settings

import quantum.api.game.data.Overload
import quantum.api.game.data.PrayerPotion

class MuspahSettings {

    val muspahNames = arrayOf("Force muspah", "Bladed muspah", "Throwing muspah")
    val loot = arrayOf("Dragon mace", "Off-hand dragon mace", "Rune full helm", "Rune platelegs", "Dragon helm")
    private val stackableLoot = arrayOf("Elder energy", "Dragon bolt tips", "Dragon arrowheads", "Death rune", "Blood rune", "Coins")
    val allLoot = loot + stackableLoot

    var prayerPotion: PrayerPotion? = null
    var overload: Overload? = null
}