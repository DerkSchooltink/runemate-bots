package bots.premium.thieving.task

import bots.premium.thieving.ElvesThieving
import bots.premium.thieving.data.Clan
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Distance
import java.util.*

class TraverseToClan : ExtendedTask<ElvesThieving>() {

    private var currentClan: Clan? = null
    private var elf: Npc? = null

    override fun validate(): Boolean {
        currentClan = Arrays.stream(Clan.values()).filter { it.isRequirementMet() && it.isThievable() }.findFirst().orElse(null)

        if (currentClan == null) return false

        elf = Npcs.newQuery().names(currentClan?.workerName).actions("Pickpocket").results().random()

        return !Inventory.isFull() && currentClan != null && Distance.to(currentClan?.coordinate) > 15 && (elf == null || elf?.isVisible == false)
    }

    override fun execute() {
        logger.info("Walking to ${currentClan?.workerName}...")
        PathBuilder.buildTo(currentClan?.coordinate)?.step()
    }
}