package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.script.Execution

class LootItems : ExtendedTask<WoodCutting>() {

    override fun validate() = bot.settings.isLootingItems.value
            && GroundItems.newQuery().names(*bot.settings.loot.toTypedArray()).results().isNotEmpty()
            && !Inventory.isFull()
            && !bankIsOpen()

    override fun execute() {
        GroundItems.newQuery().names(*bot.settings.loot.toTypedArray()).results().nearest()?.run {
            if (turnAndInteract("Take")) {
                logger.info("Looting ${definition?.name}...")
                Execution.delayUntil({ !isValid }, 1200, 1800)
            }
        }
    }

    private fun bankIsOpen() = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
}
