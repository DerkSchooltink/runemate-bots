package bots.premium.ascension.settings

import quantum.api.game.data.Food
import quantum.api.game.data.Potion
import quantum.api.game.data.PrayerPotion
import quantum.api.game.data.WeaponPoison

class AscensionSettings {
    val keys = setOf(
            "Ascension Keystone Tertius",
            "Ascension Keystone Sextus",
            "Ascension Keystone Secundus",
            "Ascension Keystone Quintus",
            "Ascension Keystone Quartus",
            "Ascension Keystone Primus"
    )

    val loot = setOf(
            "Sirenic scale",
            "Ascension grips",
            "Starved ancient effigy"
    ) + keys

    val inventionloot = setOf(
            "Runite limbs",
            "Adamant longsword",
            "Mithril platebody"
    )

    var isUsingFood: Boolean = false
    var isUsingInvention: Boolean = false
    var isUsingPrayer: Boolean = false
    var isUsingMagicNotepaper: Boolean = false
    var isUsingPotion: Boolean = false
    var isUsingWeaponPoison: Boolean = false
    var preset: Int = 0
    var potion: Potion? = null
    var weaponPoison: WeaponPoison? = null
    var food: Food? = null
    var prayerPotion: PrayerPotion? = null
}
