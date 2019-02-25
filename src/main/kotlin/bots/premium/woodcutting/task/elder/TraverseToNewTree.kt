package bots.premium.woodcutting.task.elder

import bots.premium.woodcutting.WoodCutting
import bots.premium.woodcutting.data.ElderTreeLocation
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.util.calculations.Distance
import java.util.*

class TraverseToNewTree : ExtendedTask<WoodCutting>() {

    private var activeElderTree: ElderTreeLocation? = null

    override fun validate(): Boolean {
        Arrays.stream(ElderTreeLocation.suitableLocations()).filter { tree -> tree.elderTree.isAvailable() }.findFirst().orElse(null)?.run {
            return !Inventory.isFull() && !bankIsOpen() && Distance.to(elderTree) > 15
                    && GameObjects.newQuery().names("Elder tree").appearance(90595, 90598).visibility(50.0).results().isEmpty()

        }
        return false
    }

    override fun execute() {
        logger.info("Walking to next elder tree...")
        PathBuilder.buildTo(activeElderTree)?.step()
    }

    private fun bankIsOpen(): Boolean = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
}
