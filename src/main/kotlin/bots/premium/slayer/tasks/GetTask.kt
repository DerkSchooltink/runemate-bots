package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class GetTask : ExtendedTask<Slayer>() {

    private var master: Npc? = null
    private val randomAssignmentOption = ChatDialog.getOption("No, give me a random assignment.")

    override fun validate(): Boolean {
        master = Npcs.newQuery().names(bot.slayerSettings.slayerMaster?.masterName).visible().results().first()
        return master != null && bot.slayerSettings.task?.amount == 0
                && (!bot.slayerSettings.usingNPCContact || Npcs.newQuery().names(bot.slayerSettings.slayerMaster?.masterName).visible().results().isNotEmpty())
    }

    override fun execute() {
        logger.info("Getting task from " + bot.slayerSettings.slayerMaster?.masterName + "...")

        val lowerTaskFromMaster = ChatDialog.getOption("No, that's okay, I'll take a task from you.")
        val nothing = ChatDialog.getOption("Er...nothing...")

        if (!chatBoxIsAvailable() || ChatDialog.getText() != null && ChatDialog.getText() == "Hello, and what do you ask of me?") {
            if (master?.interact("Get-task") == true) Execution.delayUntil({ ChatDialog.getContinue() != null }, 500, 900)
        } else ChatDialog.getContinue()?.select() ?: (lowerTaskFromMaster?.select() ?: (randomAssignmentOption?.select()
                ?: nothing?.select()))
    }

    private fun chatBoxIsAvailable() = !(ChatDialog.getContinue() == null && ChatDialog.getOptions().isEmpty())
}
