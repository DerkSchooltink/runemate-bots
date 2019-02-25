package bots.free.mining.sandstone.task

import bots.free.mining.sandstone.Sandstone
import quantum.api.core.ExtendedTask
import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.input.Keyboard
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar
import java.util.regex.Pattern
import java.util.stream.IntStream

class Drop : ExtendedTask<Sandstone>() {

    private val sandstone = Pattern.compile("Sandstone.*")

    override fun validate() = Inventory.getEmptySlots() <= QuantumPlayerSense.ALMOST_EMPTY_INVENTORY_SLOTS.asInteger()
            && Inventory.containsAnyOf(sandstone)

    override fun execute() {
        logger.info("Dropping sandstone...")

        ChatDialog.getContinue()?.select()

        ActionBar.newQuery()
                .names(sandstone)
                .results()
                .stream()
                .filter { it.keyBind != null && it.keyBind.length == 1 }
                .forEach { slot ->
                    IntStream.range(0, Inventory.getQuantity(slot.name))
                            .mapToObj { slot.keyBind }
                            .forEach { Keyboard.typeKey(it) }
                }
    }
}