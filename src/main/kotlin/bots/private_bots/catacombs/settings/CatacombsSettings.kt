package bots.private_bots.catacombs.settings

import bots.private_bots.catacombs.data.CatacombsMonster
import quantum.api.game.data.Food
import quantum.api.game.data.Potion

class CatacombsSettings {

    val lootItemListStacks = mutableSetOf<String>()
    val lootItemList = mutableSetOf<String>()
    val lootItemBlackList = mutableSetOf<String>()
    val lootItemBlackListStacks = mutableSetOf<String>()

    var monster: CatacombsMonster? = null
    var food: Food? = null
    var strengthPotion: Potion? = null
    var attackPotion: Potion? = null
    var strengthPotionAmount: Int = 0
    var attackPotionAmount: Int = 0
    var foodAmount: Int = 0
    var lootPrice: Int = 0
    var lootStackPrice: Int = 0

    fun addToLootItemList(item: String) {
        lootItemList.add(item)
    }

    fun addToLootItemBlackList(item: String) {
        lootItemBlackList.add(item)
    }

    fun addToLootItemListStacks(item: String) {
        lootItemListStacks.add(item)
    }

    fun addToLootItemBlackListStacks(item: String) {
        lootItemBlackListStacks.add(item)
    }

    fun clearLootLists() {
        lootItemListStacks.clear()
        lootItemBlackListStacks.clear()
        lootItemList.clear()
        lootItemListStacks.clear()
    }
}