package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.script.Execution

class Eat : ExtendedTask<Ascension>() {

    private var food: SpriteItem? = null

    override fun validate(): Boolean {
        food = Inventory.newQuery().names(bot.ascensionSettings.food.toString()).results().first()
        return bot.ascensionSettings.isUsingFood && Health.getCurrentPercent() < QuantumPlayerSense.EMERGENCY_HEALTH_AMOUNT.asInteger() && food != null
    }

    override fun execute() {
        logger.info("Eating...")
        if (food?.interact("Eat") == true) {
            Execution.delayUntil({ food?.isValid == false }, 600, 1200)
        }
    }
}
