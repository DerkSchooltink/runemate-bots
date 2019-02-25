package bots.premium.woodcutting.task

import bots.premium.woodcutting.WoodCutting
import quantum.api.core.ExtendedTask
import com.runemate.game.api.script.Execution

class BreakingTask : ExtendedTask<WoodCutting>() {

    override fun validate() = bot.breakManager.isBreaking

    override fun execute() {
        logger.info("Breaking...")
        Execution.delayUntil { !bot.breakManager.isBreaking }
    }
}
