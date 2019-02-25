package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.local.Varps
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class DragonAxeSpecialAttack : ExtendedTask<WoodCutting>() {

    private val pattern = Pattern.compile("^Special Attack: (\\d+)%$")

    override fun validate() = Environment.isOSRS()
            && getSpecialAttackPercentage() == 100
            && Equipment.containsAnyOf("Dragon axe", "Infernal axe")
            && Distance.to(GameObjects.newQuery().names(*bot.settings.tree.value.trees).results().nearest()) < 5

    override fun execute() {
        logger.info("Activating special attack...")

        if (!ControlPanelTab.COMBAT_OPTIONS.isOpen || isSpecialAttackEnabled()) {
            ControlPanelTab.COMBAT_OPTIONS.open()
        }

        val specialAttackButton = Interfaces.newQuery().texts(pattern).containers(593).grandchildren(false).results().first()

        if (specialAttackButton != null && specialAttackButton.isValid && specialAttackButton.isVisible && specialAttackButton.click()) {
            Execution.delayUntil({ isSpecialAttackEnabled() }, 600, 1200)
        }

        if (!ControlPanelTab.INVENTORY.isOpen && isSpecialAttackEnabled()) {
            ControlPanelTab.INVENTORY.open()
        }
    }

    private fun isSpecialAttackEnabled(): Boolean = Varps.getAt(301).value == 1
    private fun getSpecialAttackPercentage(): Int = Varps.getAt(300).value / 10
}
