package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.location.navigation.Traversal
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToBank : ExtendedTask<WoodCutting>() {

    override fun validate() = !bot.settings.isPowerchopping.value
            && !bot.settings.isBurningLogs.value
            && !bankIsOpen()
            && (Distance.to(bot.settings.bankingLocation.value?.bankLocation) > 3 || bot.settings.isUsingClosestBank.value)
            && Inventory.isFull()

    override fun execute() {
        if (bot.settings.isUsingClosestBank.value) PathBuilder.buildTo(getClosestBankLandmark())?.step()
        else PathBuilder.buildTo(bot.settings.bankingLocation.value)?.step()
    }

    private fun getClosestBankLandmark() : Landmark {
        val travelToBankCost = Traversal.getDefaultWeb().pathBuilder.buildTo(Landmark.BANK)?.traversalCost
                ?: 9999.toDouble()
        val travelToDepositBoxCost = Traversal.getDefaultWeb().pathBuilder.buildTo(Landmark.DEPOSIT_BOX)?.traversalCost
                ?: 9999.toDouble()

        return if (travelToBankCost > travelToDepositBoxCost) Landmark.DEPOSIT_BOX else Landmark.BANK
    }

    private fun bankIsOpen(): Boolean = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
}
