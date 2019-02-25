package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.framework.extensions.withinRadius
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.input.Keyboard
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.NpcContact
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.rs3.local.hud.Powers
import com.runemate.game.api.script.Execution
import java.awt.event.KeyEvent

class GetSlayerTaskWithLunar : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.usingNPCContact
            && hasEnoughRunes()
            && readyForNewTask()
            && !Powers.Prayer.isQuickPraying()
            && isAwayFromMonsters()

    override fun execute() {
        logger.info("Using NPC contact to get new task...")
        if (ChatDialog.getContinue() == null && ChatDialog.getOptions().isEmpty()) {
            val master = Npcs.newQuery().names(bot.slayerSettings.slayerMaster?.masterName).results().nearest()
            if (master == null) {
                if (NpcContact.cast(bot.slayerSettings.slayerMaster?.contact)) {
                    Execution.delayUntil({ ChatDialog.getContinue() != null }, 5000, 5500)
                }
            } else {
                traverseToMaster(master)
            }
        } else {
            goThroughDialogue()
        }
    }

    private fun traverseToMaster(master: Npc) = when {
        master.isVisible -> master.interact("Get-task")
        else -> BresenhamPath.buildTo(master)?.step()
    }

    private fun goThroughDialogue() {
        if (ChatDialog.getText() == "Change your assigned Slayer task?" && Keyboard.typeKey(KeyEvent.VK_2)) {
            SlayerTask.updateTask(bot.slayerSettings)
        }

        val button = ChatDialog.getContinue()
        val option = ChatDialog.getOption("I need another assignment.", "No thanks, I'd prefer a regular assignment instead.")

        when {
            button != null -> button.select()
            option != null -> option.select()
        }

        SlayerTask.updateTask(bot.slayerSettings)
    }

    private fun readyForNewTask() = bot.slayerSettings.task.let { it == null || it.amount == 0 }

    private fun hasEnoughRunes() = (Inventory.getQuantity("Air rune") >= 2
            && Inventory.getQuantity("Astral rune") >= 1
            && Inventory.getQuantity("Cosmic rune") >= 1)

    private fun isAwayFromMonsters() = Npcs.newQuery().names(bot.slayerSettings.task?.names).withinRadius(25).results().isEmpty()
}
