package bots.free.prifdinnas_cleansing_crystal.task

import bots.free.prifdinnas_cleansing_crystal.PrifdinnasCleansingCrystal
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution

class OfferCrystals : ExtendedTask<PrifdinnasCleansingCrystal>() {

    override fun validate() = Inventory.containsAnyOf("Cleansing crystal")
            && Players.getLocal()?.run { animationId == -1 && !isMoving } == true
            && !shopWindowIsOpen()

    override fun execute() {
        GameObjects.newQuery().names("Corrupted Seren Stone").results().nearest()?.let {
            Execution.delay(2500, 3500)
            if (Players.getLocal()?.animationId == -1 && it.turnAndInteract("Cleanse")) {
                logger.info("Offering crystal...")
                Execution.delayUntil({ Players.getLocal()?.animationId == -1 }, 5000)
            }
        }
    }

    private fun shopWindowIsOpen() = Interfaces.newQuery().types(InterfaceComponent.Type.SPRITE).heights(15).widths(16).actions("Close").results().isNotEmpty()
}
