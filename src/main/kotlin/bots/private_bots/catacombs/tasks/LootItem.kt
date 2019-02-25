package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GroundItem
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.net.GrandExchange
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class LootItem : ExtendedTask<CatacombsSlayer>() {

    private val lootStrings = arrayOf("Giant key", "Ancient shard", "Dark totem base", "Dark totem top", "Dark totem middle")
    private var lootItem: GroundItem? = null

    override fun validate(): Boolean {
        val itemLootList = bot.botSettings.lootItemList
        val itemBlackList = bot.botSettings.lootItemBlackList
        val itemLootListNoted = bot.botSettings.lootItemListStacks
        val itemBlackListNoted = bot.botSettings.lootItemBlackListStacks

        logger.debug("Items to loot:            $itemLootList")
        logger.debug("Items to ignore:          $itemBlackList")
        logger.debug("Items to loot (stacks):   $itemLootListNoted")
        logger.debug("Items to ignore (stacks): $itemBlackListNoted")

        val loot = GroundItems.newQuery().filter { item ->
            item.definition?.let {
                if (!it.isNoted && !it.stacks() && !itemBlackList.contains(it.name)) {
                    if (itemLootList.contains(it.name)) {
                        return@filter true
                    } else {
                        val price = GrandExchange.lookup(item.id)?.price ?: 0
                        if (it.isTradeable && price > bot.botSettings.lootPrice) {
                            bot.botSettings.addToLootItemList(it.name)
                            return@filter true
                        } else {
                            bot.botSettings.addToLootItemBlackList(it.name)
                            return@filter false
                        }
                    }
                }
            }
            false
        }.withinRadius(10).results()

        val stackables = GroundItems.newQuery().filter { item ->
            var definition = item.definition
            if (definition != null && definition.isNoted) {
                definition = ItemDefinition.get(definition.unnotedId)
            }
            if (definition != null && definition.isTradeable && (definition.isNoted || definition.stacks()) && !itemBlackListNoted.contains(definition.name)) {
                if (itemLootListNoted.contains(definition.name)) {
                    return@filter true
                } else {
                    val price = GrandExchange.lookup(item.id)?.price ?: 0
                    if (price > bot.botSettings.lootStackPrice) {
                        bot.botSettings.addToLootItemListStacks(definition.name)
                        return@filter true
                    } else {
                        bot.botSettings.addToLootItemBlackListStacks(definition.name)
                        return@filter false
                    }
                }
            }
            false
        }.withinRadius(10).results()

        loot.addAll(stackables)
        loot.addAll(GroundItems.newQuery().withinRadius(10).names(*lootStrings).results())

        lootItem = loot.nearest()

        return lootItem != null && !Npcs.newQuery().names(bot.botSettings.monster?.toString()).results().isEmpty() && !Inventory.isFull() && Inventory.containsAnyOf(bot.botSettings.food.toString())
    }

    override fun execute() {
        lootItem?.let {
            if (Players.getLocal()?.isMoving == true || it.turnAndInteract("Take")) {
                logger.info("Looting " + it.definition?.name + "...")
                Execution.delayUntil({ !it.isValid }, 1800, 2100)
            }
        }
    }
}
