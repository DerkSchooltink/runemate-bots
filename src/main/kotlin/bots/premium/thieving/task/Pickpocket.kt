package bots.premium.thieving.task

import bots.premium.thieving.ElvesThieving
import bots.premium.thieving.data.Clan
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.local.Varbits
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class Pickpocket : ExtendedTask<ElvesThieving>() {

    private var currentClan: Clan? = null
    private var elf: Npc? = null

    override fun validate(): Boolean {
        if (Bank.isOpen()) Bank.close()
        currentClan = Clan.values().firstOrNull { it.isRequirementMet() && it.isThievable() } ?: return false
        elf = Npcs.newQuery().names(currentClan?.workerName).actions("Pickpocket").results().random()
        return !Inventory.isFull() && elf != null && (!bot.settings.useCrystalMask || isCrystalMaskActivated() || Skill.MAGIC.currentLevel < 90)
    }

    override fun execute() {
        if (Players.getLocal()?.animationId == -1) {
            elf?.let {
                if (it.turnAndInteract("Pickpocket")) {
                    Execution.delayUntil({ Players.getLocal()?.animationId == -1 }, { Players.getLocal()?.run { animationId != -1 || isMoving }}, 5600, 3600, 7600)
                }
            }
        }
    }

    private fun isCrystalMaskActivated() = Varbits.load(29116)?.value == 1
}