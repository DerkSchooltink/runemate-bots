package bots.private_bots.catacombs.tasks

import bots.private_bots.catacombs.CatacombsSlayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class PerformSpecialAttack : ExtendedTask<CatacombsSlayer>() {

    override fun validate() = !isSpecialAttackEnabled()
            && !Npcs.newQuery().names(bot.botSettings.monster.toString()).results().isEmpty()
            && getSpecialAttackPercentage() >= 25

    override fun execute() {
        logger.info("Activating special attack...")

        if (!ControlPanelTab.COMBAT_OPTIONS.isOpen || isSpecialAttackEnabled()) {
            ControlPanelTab.COMBAT_OPTIONS.open()
        }

        val specialAttackButton = Interfaces.newQuery().texts(Pattern.compile("^Special Attack: (\\d+)%$")).containers(593).grandchildren(false).results().first()

        if (isSpecialAttackEnabled() || (specialAttackButton != null && specialAttackButton.isValid && specialAttackButton.isVisible && specialAttackButton.click())) {
            Execution.delayUntil({ isSpecialAttackEnabled() }, 600, 1200)
        }
    }

    private fun getSpecialAttackPercentage(): Int = Varps.getAt(300).value / 10
    private fun isSpecialAttackEnabled(): Boolean = Varps.getAt(301).value == 1
}