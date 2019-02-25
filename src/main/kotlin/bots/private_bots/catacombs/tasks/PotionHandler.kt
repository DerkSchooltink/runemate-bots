package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import quantum.api.game.data.Potion
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players

class PotionHandler : ExtendedTask<CatacombsSlayer>() {

    private var strengthPotion: Potion? = null
    private var attackPotion: Potion? = null
    private var boostedAmountStrength: Int = 0
    private var boostedAmountAttack: Int = 0

    override fun validate(): Boolean {
        boostedAmountAttack = Skill.ATTACK.currentLevel - Skill.ATTACK.baseLevel
        boostedAmountStrength = Skill.STRENGTH.currentLevel - Skill.STRENGTH.baseLevel

        strengthPotion = bot.botSettings.strengthPotion
        attackPotion = bot.botSettings.attackPotion

        val player = Players.getLocal() ?: return false

        val monster = Npcs.newQuery().names(bot.botSettings.monster?.name).within(Area.Circular(player.position, 20.0)).results().nearest()

        return (monster != null && player.target != null
                && (bot.botSettings.strengthPotionAmount != 0 && strengthPotion != null && boostedAmountStrength < 2 || bot.botSettings.attackPotionAmount != 0 && attackPotion != null && boostedAmountAttack < 2))
    }

    override fun execute() {
        logger.info("Drinking potion...")
        if (strengthPotion != null && boostedAmountStrength < 2) {
            drink(strengthPotion)
        } else if (attackPotion != null && boostedAmountAttack < 2) {
            drink(attackPotion)
        }
    }

    private fun drink(potion: Potion?) = Inventory.newQuery().names(potion?.pattern).results().first()?.let {
        Inventory.containsAnyOf(it.definition?.name) && it.definition?.inventoryActions?.contains("Drink") == true && it.interact("Drink")
    }
}
