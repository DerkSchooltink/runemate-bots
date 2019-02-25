package quantum.api.game

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionWindow
import com.runemate.game.api.script.Execution

object Invention {

    private const val WIDGET_INDEX = 1461
    private const val PROGRESS_WINDOW_WIDGET_INDEX = 1251

    private val container: InterfaceComponent? = Interfaces.newQuery().containers(WIDGET_INDEX).types(InterfaceComponent.Type.CONTAINER).results().first()
    private val disassembleComponent: InterfaceComponent? = Interfaces.newQuery().names("Disassemble").containers(
        WIDGET_INDEX
    ).types(InterfaceComponent.Type.SPRITE).results().first()

    fun getProgressWindowContainer(): InterfaceComponent? {
        return Interfaces.newQuery().containers(PROGRESS_WINDOW_WIDGET_INDEX).results().first()
    }

    private fun castSpell(): Boolean {
        if (disassembleComponent == null || container == null) return false
        if (!ActionWindow.MAGIC_BOOK.isOpen && !disassembleComponent.isVisible) ActionWindow.MAGIC_BOOK.open()
        return Interfaces.scrollTo(disassembleComponent, container) && disassembleComponent.interact("Activate")
    }

    fun disassemble(item: SpriteItem?): Boolean {
        val progressInterfaceVisible = getProgressWindowContainer()?.isVisible ?: false
        if (item == null) return false
        return castSpell()
                && item.interact("Activate", "Disassemble -> " + item.definition?.name)
                && Execution.delayUntil({ progressInterfaceVisible }, 1200, 1800)
    }

    fun disassemble(item: String): Boolean {
        val inventoryItem = Inventory.newQuery().names(item).results().first()
        return inventoryItem != null && castSpell() && inventoryItem.interact("Activate", "Disassemble -> $item") && Execution.delayUntil({ getProgressWindowContainer()?.isVisible == false }, 1200, 1800)
    }
}
