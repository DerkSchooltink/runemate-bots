package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import bots.premium.woodcutting.data.ChopLocation
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.util.calculations.Distance

class WalkToTrees : ExtendedTask<WoodCutting>() {

    private lateinit var location: Locatable

    override fun validate(): Boolean {
        location = getLocation() ?: return false
        return !Inventory.isFull()
                && !bankIsOpen()
                && Distance.to(location) > bot.settings.maximumDistanceToTree
    }

    override fun execute() {
        logger.info("Walking to trees...")
        PathBuilder.buildTo(location)?.step()
    }

    private fun getLocation(): Locatable? {
        val chopLocation = bot.settings.chopLocation.value
        val tree = bot.settings.tree.value
        return when (chopLocation) {
            is ChopLocation -> chopLocation.map[tree]
            is Coordinate -> chopLocation
            else -> GameObjects.newQuery().names(*tree.trees).results().nearest()?.position
        }
    }

    private fun bankIsOpen() = Shop.isOpen() || Bank.isOpen() || DepositBox.isOpen()
}
