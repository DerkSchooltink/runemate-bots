package bots.premium.woodcutting.task.firemaking

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.useOn
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class BurnLogs : ExtendedTask<WoodCutting>() {

    override fun validate() = Players.getLocal()?.animationId == -1
            && bot.settings.isBurningLogs.value
            && Inventory.isFull()

    override fun execute() {
        val logs = Inventory.newQuery().actions("Light").results().first()
        val fire = GameObjects.newQuery().names("Fire").actions("Use").results().nearest()

        if (fire != null) {
            if (fire.visibility > 50) {
                if (Inventory.getSelectedItem()?.definition?.inventoryActions?.contains("Light") == true) {
                    if (logs.useOn(fire)) {
                        Execution.delayUntil({ Inventory.isEmpty() && Players.getLocal()?.animationId == -1 }, { Players.getLocal()?.animationId != -1 }, 3600, 4200)
                    }
                } else {
                    if (logs?.interact("Use") == true) {
                        Execution.delayUntil({ Inventory.getSelectedItem() == logs }, 600, 1200)
                    }
                }
            } else {
                Camera.concurrentlyTurnTo(fire)
                PathBuilder.buildTo(fire)?.step()
            }
        } else {
            if (logs?.interact("Light") == true) {
                logger.info("Burning logs...")
                Execution.delayUntil({ logs.isValid }, 1200, 1800)
            }
        }
    }
}
