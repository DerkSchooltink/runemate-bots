package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.game.data.Potion
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players

class PotionHandler : ExtendedTask<Ascension>() {

    private var potion: Potion? = null

    override fun validate(): Boolean {
        val player = Players.getLocal() ?: return false

        val monster = Npcs.newQuery().names("Rorarius").within(Area.Circular(player.position, 20.0)).results().nearest()

        potion = bot.ascensionSettings.potion

        return monster != null && player.target != null && bot.ascensionSettings.isUsingPotion && potion != null && belowBoostedThreshold()
    }

    override fun execute() {
        logger.info("Drinking " + potion.toString() + "...")

        val pot = Inventory.newQuery().names(potion?.pattern).results().first()

        if (Inventory.containsAnyOf(pot?.definition?.name) && pot?.definition?.inventoryActions?.contains("Drink") == true) {
            pot.interact("Drink")
        }
    }

    private fun belowBoostedThreshold() = Skill.RANGED.currentLevel - Skill.RANGED.baseLevel < 2
}
