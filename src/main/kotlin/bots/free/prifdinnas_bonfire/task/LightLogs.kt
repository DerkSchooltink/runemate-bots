package bots.free.prifdinnas_bonfire.task

import bots.free.prifdinnas_bonfire.PrifdinnasBonfire
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class LightLogs : ExtendedTask<PrifdinnasBonfire>() {

    override fun validate() = (Npcs.newQuery().names("Fire spirit").actions("Collect-reward").results().isEmpty() || Inventory.getEmptySlots() < 6)
            && Players.getLocal()?.animationId == -1
            && !Bank.isOpen()
            && Inventory.containsAnyOf(Pattern.compile(".* logs.*", Pattern.CASE_INSENSITIVE))

    override fun execute() {
        GameObjects.newQuery().names("Bonfire").results().nearest()?.let {
            if (it.turnAndInteract("Add logs to")) {
                logger.info("Adding logs to fire...")
                Execution.delayUntil({ Inventory.getQuantity(Pattern.compile(".*logs.*")) == 0 || Inventory.getEmptySlots() >= bot.amountToWaitForLooting && !Npcs.newQuery().names("Fire spirit").actions("Collect-reward").results().isEmpty() }, { Players.getLocal()?.animationId == -1 }, 4500, 5000)
            }
        }
    }
}
