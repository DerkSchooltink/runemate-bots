package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.Interaction
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players

class DrinkOverload : ExtendedTask<Muspah>() {

    override fun validate(): Boolean {
        val monster = Npcs.newQuery().names(*bot.settings.muspahNames).withinRadius(25).results().nearest()
        return monster != null && Players.getLocal()?.target != null && bot.settings.overload != null && belowBoostedThreshold()
    }

    override fun execute() {
        if (Inventory.getSelectedItem() != null) Interaction.deselectItem()
        Inventory.newQuery().names(bot.settings.overload?.pattern).results().first()?.let {
            if (Inventory.containsAnyOf(it.definition?.name) && it.definition?.inventoryActions?.contains("Drink") == true) {
                if (it.interact("Drink")) {
                    logger.info("Drinking " + bot.settings.overload.toString() + "...")
                }
            }
        }
    }

    private fun belowBoostedThreshold() = Skill.RANGED.run { currentLevel - baseLevel < 2 }
}