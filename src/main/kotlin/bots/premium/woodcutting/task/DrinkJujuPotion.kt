package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.Varbits
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.script.Execution

class DrinkJujuPotion : ExtendedTask<WoodCutting>() {

    override fun validate() = !bot.settings.isUsingJujuPotion.value
            && Inventory.containsAnyOf(bot.settings.jujuPattern)
            && !jujuActive()

    override fun execute() {
        if (Inventory.newQuery().names(bot.settings.jujuPattern).results().first()?.interact("Drink") == true) {
            logger.info("Drinking juju potion...")
            Execution.delayUntil(::jujuActive, 1200, 1800)
        }
    }

    private fun jujuActive() = Varbits.load(25856)?.value != 0
}
