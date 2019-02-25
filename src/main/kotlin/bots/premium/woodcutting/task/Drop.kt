package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.game.DropMethod
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.Items
import com.runemate.game.api.osrs.local.hud.interfaces.OptionsTab
import java.util.regex.Pattern

class Drop : ExtendedTask<WoodCutting>() {

    private val logs = Pattern.compile(".*(logs|Logs)")

    override fun validate() = !bot.settings.isBurningLogs.value
            && InterfaceWindows.getInventory().isOpen
            && bot.settings.isPowerchopping.value
            && Inventory.containsAnyOf(logs)
            && playerIsMoving() == false
            && Inventory.getSelectedItem() == null
            && Inventory.isFull()

    override fun execute() {
        logger.info("Dropping...")
        if (Environment.isOSRS() && !OptionsTab.isShiftDroppingEnabled()) {
            OptionsTab.setShiftDropping(true)
        } else {
            DropMethod.drop(queryResults = Inventory.newQuery().actions("Drop").filter(Items.getNamePredicate<SpriteItem>(logs)).results())
        }
    }

    private fun playerIsMoving() = Players.getLocal()?.run { animationId != -1 || isMoving }
}
