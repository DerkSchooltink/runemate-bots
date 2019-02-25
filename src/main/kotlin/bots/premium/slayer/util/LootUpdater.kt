package bots.premium.slayer.util

import bots.premium.slayer.Slayer
import quantum.api.framework.extensions.names
import quantum.api.framework.extensions.withinRadius
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.net.GrandExchange
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.framework.core.LoopingThread

class LootUpdater(private val bot: Slayer) {

    fun initialize() {
        LoopingThread({
            lootUpdater(bot)
        }, 1500).start()
    }

    private val stackableLootMinimumPrice = 250

    private val minimumLootPrice = bot.slayerSettings.lootPriceMinimum
    private val itemLootList = bot.slayerSettings.lootItemList
    private val itemBlackList = bot.slayerSettings.lootItemBlackList
    private val itemLootListNoted = bot.slayerSettings.lootItemListStacks
    private val itemBlackListNoted = bot.slayerSettings.lootItemBlackListStacks

    private var lootItem: GroundItem? = null

    fun getLootOrNull() = lootItem

    private fun lootUpdater(bot: Slayer) {
        if (Npcs.newQuery().names(bot.slayerSettings.task?.names).results().isNotEmpty()) {

            bot.logger.debug("Items to loot:            $itemLootList")
            bot.logger.debug("Items to ignore:          $itemBlackList")
            bot.logger.debug("Items to loot (stacks):   $itemLootListNoted")
            bot.logger.debug("Items to ignore (stacks): $itemBlackListNoted")

            lootItem = GroundItems.newQuery().filter { item ->
                item.definition?.let { definition ->
                    lootCoinsFilter(item) ||
                            lootRegularItemsFilter(definition) ||
                            lootStackablesFilter(definition) ||
                            defaultLootFilter(definition)
                } ?: false
            }.withinRadius(15).results().nearest()
        }
    }

    private fun defaultLootFilter(definition: ItemDefinition) = bot.slayerSettings.lootItems.isNotEmpty() && bot.slayerSettings.lootItems.contains(definition.name)

    private fun lootCoinsFilter(item: GroundItem) = item.definition?.name == "Coins" && item.quantity >= stackableLootMinimumPrice

    private fun lootRegularItemsFilter(definition: ItemDefinition) = if (
            !definition.isNoted &&
            !definition.stacks() &&
            !itemBlackList.contains(definition.name)
    ) {
        if (itemLootList.contains(definition.name)) true else {
            GrandExchange.lookup(definition.id)?.price?.let {
                definition.isTradeable && it > minimumLootPrice
            } ?: false
        }
    } else false

    private fun lootStackablesFilter(definition: ItemDefinition): Boolean {
        return if (bot.slayerSettings.lootStackables) {
            if (Inventory.contains(definition.id)) return true

            var mutableDefinition = definition

            val isNoted = mutableDefinition.isNoted
            val unnotedDefinition = (if (isNoted) {
                ItemDefinition.get(mutableDefinition.unnotedId)
            } else mutableDefinition) ?: return false

            if (unnotedDefinition.isTradeable && (isNoted || mutableDefinition.stacks())) {
                if (mutableDefinition.isNoted) mutableDefinition = ItemDefinition.get(mutableDefinition.unnotedId)
                        ?: return false
                val price = GrandExchange.lookup(mutableDefinition.id)?.price ?: -1
                price > stackableLootMinimumPrice
            } else false
        } else false
    }
}