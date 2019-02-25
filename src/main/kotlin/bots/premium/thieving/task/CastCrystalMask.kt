package bots.premium.thieving.task

import bots.premium.thieving.ElvesThieving
import bots.premium.thieving.data.Clan
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.Varbits
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.rs3.local.hud.Powers
import com.runemate.game.api.script.Execution
import java.util.*

class CastCrystalMask : ExtendedTask<ElvesThieving>() {

    override fun validate() = bot.settings.useCrystalMask
            && !isCrystalMaskActivated()
            && Skill.MAGIC.currentLevel >= 90
            && hasEnoughRunes()
            && Arrays.stream(Clan.values()).filter { it.isRequirementMet() && it.isThievable() }.findFirst().isPresent

    override fun execute() {
        if (Powers.Magic.Ancient.CRYSTAL_MASK.activate()) {
            Execution.delayUntil(::isCrystalMaskActivated, 1200, 1600)
        }
    }

    private fun isCrystalMaskActivated() = Varbits.load(29116)?.value == 1

    private fun hasEnoughRunes() = Inventory.getQuantity("Soul rune") >= 4
            && Inventory.getQuantity("Body rune") >= 5
            && Inventory.getQuantity("Fire rune") >= 6
            && Inventory.getQuantity("Earth rune") >= 7
}