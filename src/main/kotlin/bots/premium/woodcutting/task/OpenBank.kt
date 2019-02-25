package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.navigation.BankLocation
import quantum.api.game.navigation.ShopLocation
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.local.hud.interfaces.*
import com.runemate.game.api.hybrid.region.Banks
import com.runemate.game.api.hybrid.region.DepositBoxes
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class OpenBank : ExtendedTask<WoodCutting>() {

    private lateinit var entity: LocatableEntity
    private lateinit var bankingLocation: Locatable

    override fun validate(): Boolean {
        bankingLocation = bot.settings.bankingLocation.value?.bankLocation ?: return false
        entity = getBankEntity() ?: return false

        return !bankIsOpen()
                && !bot.settings.isBurningLogs.value
                && entity.isVisible
                && Inventory.isFull()
    }

    override fun execute() {
        if (ChatDialog.getContinue() != null) ChatDialog.getContinue()?.select() else {
            if (bankingLocation is ShopLocation) {
                if (entity.turnAndInteract((bankingLocation as ShopLocation).action)) {
                    Execution.delayUntil({ Shop.isOpen() }, 1200, 1800)
                }
            } else if (bankingLocation is BankLocation) {
                interactWithBank((bankingLocation as BankLocation).type)
            }
        }
    }

    private fun bankIsOpen() = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()

    private fun interactWithBank(type: BankLocation.Type) = when (type) {
        BankLocation.Type.DEPOSIT -> DepositBox.open()
        BankLocation.Type.FULL -> Bank.open()
        BankLocation.Type.SPECIAL -> {
            if (Banks.newQuery().withinRadius(10).results().isEmpty()) DepositBox.open()
            else Bank.open()
        }
        else -> Bank.open()
    }

    private fun getBankEntity() = if (bankingLocation is ShopLocation) {
        Npcs.newQuery().names((bankingLocation as ShopLocation).entityName).results().nearest()
    } else {
        Banks.newQuery().withinRadius(10).results().nearest() ?: DepositBoxes.getLoaded().nearest()
    }
}
