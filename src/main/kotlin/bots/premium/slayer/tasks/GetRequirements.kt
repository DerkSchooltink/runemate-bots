package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.script.Execution

class GetRequirements : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.task?.monster?.requirements?.hasEquipment() == false

    override fun execute() {
        logger.info("Getting proper requirements...")
        if (!Bank.isOpen()) {
            Banks.newQuery().results().nearest()?.let {
                if (it.turnAndInteract("Bank")) Execution.delayUntil(Bank::isOpen, 1200, 1800)
            }
        } else {
            bot.slayerSettings.task?.requirements?.specialItemsAsStringArray?.toTypedArray().orEmpty().let {
                if (!Inventory.containsAllOf(*it)) {
                    Inventory.equip(*Inventory.newQuery().names(*it).actions("Wear").results().toArray())
                }
            }
        }
    }
}

