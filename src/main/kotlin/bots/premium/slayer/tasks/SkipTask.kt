package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import bots.premium.slayer.frame.SlayerTask
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.withinRadius
import quantum.api.game.PathBuilder
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.region.Npcs

class SkipTask : ExtendedTask<Slayer>() {

    override fun validate() = bot.slayerSettings.skipTasks.contains(bot.slayerSettings.task?.monster) && bot.slayerSettings.task?.amount != 0

    override fun execute() {
        logger.info("Skipping task...")

        val master = Npcs.newQuery().names(bot.slayerSettings.slayerMaster?.masterName).withinRadius(15).results().nearest()
        val assignmentButton = Interfaces.getAt(1308, 507)
        val buyButton = Interfaces.getAt(1308, 715)
        val closeButton = Interfaces.getAt(1308, 472)
        val notOnTaskInterface = Interfaces.newQuery().texts("Not on task").results().first()

        if (buttonIsNotVisible(notOnTaskInterface)) {
            closeButton?.let { if (it.isVisible) it.interact("Close") }
        }

        if (master != null && buttonIsNotVisible(assignmentButton)) {
            master.interact("Rewards")
        }

        if (buttonIsVisible(assignmentButton) && buttonIsNotVisible(buyButton)) {
            assignmentButton?.interact("Select")
        }

        if (buttonIsVisible(buyButton)) {
            if (buyButton?.interact("Buy") == true) {
                SlayerTask.updateTask(bot.slayerSettings)
                logger.debug("Resetting task...")
            }
        }

        if (master == null && buttonIsNotVisible(assignmentButton) && buttonIsNotVisible(buyButton)) {
            PathBuilder.buildTo(bot.slayerSettings.slayerMaster)?.step()
        }
    }

    private fun buttonIsNotVisible(component: InterfaceComponent?) = component == null || !component.isVisible
    private fun buttonIsVisible(component: InterfaceComponent?) = component?.isVisible == true
}
