package bots.premium.divination.task

import bots.premium.divination.HallOfMemories
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class CaptureFragment : ExtendedTask<HallOfMemories>() {

    private var fragment: Npc? = null

    override fun validate(): Boolean {
        fragment = Npcs.newQuery().names(*bot.settings.fragmentNames).actions("Capture").results().nearest()
        return fragment != null && Inventory.containsAnyOf(*bot.settings.jarNames) && !Inventory.isFull()
    }

    override fun execute() {
        fragment?.let {
            if (it.turnAndInteract("Capture")) {
                logger.info("Capturing fragment...")
                Execution.delayUntil({ Players.getLocal()?.isMoving == false }, { Players.getLocal()?.isMoving == true }, 1600, 2200)
            }
        }
    }
}