package bots.free.prifdinnas_cleansing_crystal.task

import bots.free.prifdinnas_cleansing_crystal.PrifdinnasCleansingCrystal
import quantum.api.core.ExtendedTask
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.input.Keyboard
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.rs3.local.hud.interfaces.MoneyPouch
import com.runemate.game.api.script.Execution
import java.awt.event.KeyEvent

class BuyCrystals : ExtendedTask<PrifdinnasCleansingCrystal>() {

    private var monk: Npc? = null

    override fun validate(): Boolean {
        monk = Npcs.newQuery().names("Hefin monk").results().nearest()
        return Players.getLocal()?.animationId == -1
                && monk != null
                && MoneyPouch.getContents() > 550000
                && (!Inventory.contains("Cleansing crystal") || !Interfaces.newQuery().types(InterfaceComponent.Type.SPRITE).heights(15).widths(16).actions("Close").results().isEmpty())
    }

    override fun execute() {
        logger.info("Buying new crystals...")
        val buyCrystalButton = Interfaces.getAt(1265, 253)
        val shopInterface = Interfaces.getAt(1265, 20, 0)
        val closeButton = Interfaces.newQuery().types(InterfaceComponent.Type.SPRITE).heights(15).widths(16).actions("Close").results().first()

        if (shopInterface == null || !shopInterface.isVisible) {
            monk.turnAndInteract("TradeRequest with")
        } else {
            if (buyCrystalButton == null || !buyCrystalButton.isVisible) {
                if (!Inventory.contains("Cleansing crystal")) {
                    if (shopInterface.interact("Buy 5")) {
                        if (closeButton == null || !closeButton.isVisible) {
                            Execution.delayUntil({ buyCrystalButton != null && buyCrystalButton.isVisible }, 1200, 1800)
                        }
                    }
                } else {
                    logger.info("Closing shop window...")
                    closeButton?.interact("Close")
                }
            } else {
                if (buyCrystalButton.click()) {
                    Execution.delayUntil({ Inventory.containsAnyOf("Cleansing crystal") }, 1200, 1800)
                    Keyboard.typeKey(KeyEvent.VK_ESCAPE)
                }
            }
        }
    }
}
