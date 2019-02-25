package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.script.Execution
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense

class EatFood : ExtendedTask<CatacombsSlayer>() {

    private var food: SpriteItem? = null

    override fun validate(): Boolean {
        food = Inventory.newQuery().actions("Eat").results().random()
        return Health.getCurrentPercent() < 50 && food != null && !Bank.isOpen()
    }

    override fun execute() {
        logger.info("Eating...")
        repeat((0 until QuantumPlayerSense.SPAM_CLICK_COUNT.asInteger()).count()) {
            food?.let {
                if (it.interact("Eat")) {
                    Execution.delayUntil({ it.isValid }, 800, 1200)
                }
            }
        }
    }
}
