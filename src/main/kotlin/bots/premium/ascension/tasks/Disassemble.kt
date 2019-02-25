package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.game.Invention
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.script.Execution

class Disassemble : ExtendedTask<Ascension>() {

    private var itemToDestroy: SpriteItem? = null

    override fun validate(): Boolean {
        if (!bot.ascensionSettings.isUsingInvention) return false


        if (Inventory.containsAnyOf("Runite limbs", "Adamant longsword", "Mithril platebody")) {
            itemToDestroy = Inventory.newQuery().names("Runite limbs", "Adamant longsword", "Mithril platebody").filter { item -> item != null && item.definition != null && item.definition?.stacks() == false }.results().first()
            return itemToDestroy != null
        }

        return false
    }

    override fun execute() {
        logger.info("Disassembling item...")
        val progressWindow = Invention.getProgressWindowContainer()
        if (progressWindow == null || !progressWindow.isVisible) {
            if (Invention.disassemble(itemToDestroy)) {
                Execution.delayUntil({ progressWindow == null || !progressWindow.isVisible }, 1500, 2500)
            }
        }
    }
}
