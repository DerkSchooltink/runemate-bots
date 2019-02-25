package bots.premium.woodcutting.task.elder

import bots.premium.woodcutting.WoodCutting
import bots.premium.woodcutting.data.ElderTree
import bots.premium.woodcutting.data.ElderTreeLocation
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class ChopElderTree : ExtendedTask<WoodCutting>() {

    private var tree: GameObject? = null
    private var activeElderTree: ElderTree? = null

    override fun validate(): Boolean {
        if (activeElderTree != null) {
            tree = GameObjects.newQuery().names("Elder tree").results().nearest()
        } else {
            activeElderTree = ElderTreeLocation.randomSuitableLocation().elderTree
        }

        if (tree?.definition?.localState?.appearance?.contains(90596) == true) {
            activeElderTree?.setAvailable(false)
            ElderTreeLocation.suitableLocations().firstOrNull { tree -> tree.elderTree.isAvailable() }?.let { active -> activeElderTree = active.elderTree }
            return false
        }

        return Players.getLocal().let { it?.animationId == -1 && !it.isMoving }
                && !Inventory.isFull()
                && !bankIsOpen()
                && Inventory.getSelectedItem() == null
    }

    override fun execute() {
        tree?.let {
            it.setForcedModel(intArrayOf(-10, -10, -10), intArrayOf(10, 10, 10))
            if (it.visibility < 20) {
                Camera.concurrentlyTurnTo(it)
                BresenhamPath.buildTo(it)?.run { step() }
            }
            if (Players.getLocal()?.animationId == -1 && it.interact("Chop down")) {
                logger.info("Chopping elder tree...")
                Execution.delayUntil({ !it.isValid || !GroundItems.newQuery().names(*bot.settings.loot.toTypedArray()).results().isEmpty() }, { it.animationId != -1 }, 1200, 1800)
            }
        }
    }

    private fun bankIsOpen(): Boolean = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
}
