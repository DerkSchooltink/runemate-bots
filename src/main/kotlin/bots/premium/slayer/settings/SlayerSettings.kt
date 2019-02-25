package bots.premium.slayer.settings

import bots.premium.slayer.data.Monster
import bots.premium.slayer.data.SlayerMaster
import bots.premium.slayer.frame.SlayerTask
import quantum.api.game.data.Antifire
import quantum.api.game.data.PrayerPotion
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class SlayerSettings {

    val lootItemList = mutableSetOf<String>()
    val lootItemBlackList = mutableSetOf<String>()
    val lootItemListStacks = mutableSetOf<String>()
    val lootItemBlackListStacks = mutableSetOf<String>()

    var task: SlayerTask? = null
    var slayerMaster: SlayerMaster? = null
    val skipTasks: ObservableList<Monster> = FXCollections.observableArrayList<Monster>()

    var usingStandardLootList = false
    var useQuickPrayer = false
    var usingNPCContact = false

    var lootStackables = false
    var lootPriceMinimum = 5000

    var antifirePotion: Antifire? = null
    var prayerPotion: PrayerPotion? = null
    var lootItems: ObservableList<String> = FXCollections.observableArrayList<String>()
}