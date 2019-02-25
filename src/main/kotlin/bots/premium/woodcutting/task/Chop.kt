package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import bots.premium.woodcutting.data.ChopLocation
import bots.premium.woodcutting.data.Tree
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.setForcedModel
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.DepositBox
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.Shop
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class Chop : ExtendedTask<WoodCutting>() {

    private lateinit var tree: GameObject
    private lateinit var chopLocation: ChopLocation

    override fun validate(): Boolean {
        chopLocation = bot.settings.chopLocation.value ?: return bot.settings.isPowerchopping.value
        tree = getNearestTree(bot.settings.tree.value) ?: return false

        return !playerIsMoving()
                && !Inventory.isFull()
                && !bankIsOpen()
                && !hasSpecialLogs()
                && Inventory.getSelectedItem() == null
                && getDistanceToTree() <= bot.settings.maximumDistanceToTree
    }

    override fun execute() {
        if (!canInteractWithTree(tree)) {
            BresenhamPath.buildTo(tree)?.step()
        } else if (playerIsMoving() || tree.turnAndInteract(getFirstAction(tree))) {
            logger.info("Chopping " + tree.definition?.name + "...")
            Execution.delayUntil({ !tree.isValid || lootAvailable() }, { playerIsMoving() }, 1400, 1800)
        }
    }

    private fun getNearestTree(treeType: Tree): GameObject? = GameObjects
            .newQuery()
            .types(getType(treeType))
            .filter { chopLocation != ChopLocation.MENAPHOS_VIP || bot.settings.vipAreaCoordinates.contains(it.position) }
            .withinRadius(bot.settings.maximumDistanceToTree + 1)
            .names(*treeType.trees)
            .surroundingsReachable()
            .actions("Chop down", "Cut", "Chop", "Cut down")
            .results()
            .nearest()
            .setForcedModel(treeType.forcedModel)

    private fun hasSpecialLogs() = Inventory.containsAnyOf("Special teak log", "Special mahogany log") && bot.settings.isPowerchopping.value
    private fun bankIsOpen() = Bank.isOpen() || DepositBox.isOpen() || Shop.isOpen()
    private fun getFirstAction(tree: GameObject?) = tree?.definition?.actions?.first() ?: "Chop down"
    private fun getType(treeType: Tree?) = if (treeType == Tree.IVY) GameObject.Type.WALL_DECORATION else GameObject.Type.PRIMARY
    private fun playerIsMoving() = Players.getLocal()?.run { animationId != -1 || isMoving } == true
    private fun canInteractWithTree(tree: GameObject) = Distance.to(tree) <= 5 || tree.isVisible
    private fun lootAvailable() = GroundItems.newQuery().names(*bot.settings.loot.toTypedArray()).results().isNotEmpty()

    private fun getDistanceToTree() =
            if (bot.settings.isPowerchopping.value) Distance.to(GameObjects.newQuery().names(*bot.settings.tree.value.trees).results().nearest())
            else Distance.to(chopLocation.map[bot.settings.tree.value])
}
