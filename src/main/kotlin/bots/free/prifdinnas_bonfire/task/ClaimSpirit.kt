package bots.free.prifdinnas_bonfire.task

import bots.free.prifdinnas_bonfire.PrifdinnasBonfire
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.script.Execution

class ClaimSpirit : ExtendedTask<PrifdinnasBonfire>() {

    private var fireSpirit: Npc? = null

    override fun validate(): Boolean {
        fireSpirit = Npcs.newQuery().names("Fire spirit").actions("Collect-reward").results().first()
        return !Bank.isOpen() && Inventory.getEmptySlots() >= bot.amountToWaitForLooting && fireSpirit != null
    }

    override fun execute() {
        logger.info("Catching fire spirit...")
        fireSpirit?.let {
            if (it.interact("Collect-reward")) {
                bot.amountToWaitForLooting = Random.nextInt(6, 10)
                logger.debug("Empty inventory spots to wait for looting: ${bot.amountToWaitForLooting}")
                Execution.delayUntil({ !it.isValid }, 1200, 1800)
            }
        }
    }
}
