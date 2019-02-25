package quantum.api.game

import com.runemate.game.api.hybrid.input.Keyboard
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.queries.results.SpriteItemQueryResults
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import java.awt.event.KeyEvent
import java.util.*
import java.util.regex.Pattern

object DropMethod {
    fun drop(shift: Boolean, names: Pattern) {
        var list: MutableList<SpriteItem> = Inventory.newQuery().names(names).results().asList()
        list = when {
            Random.nextBoolean() -> snake(list)
            else -> topBottom(list)
        }
        if (!shift || Keyboard.pressKey(KeyEvent.VK_SHIFT)) {
            list.stream().filter { item -> item.isValid && item.isVisible }.forEach { item -> item.interact("Drop") }
        }
        Keyboard.releaseKey(KeyEvent.VK_SHIFT)
    }

    fun drop(shift: Boolean = true, queryResults: SpriteItemQueryResults?) {
        if (!ControlPanelTab.INVENTORY.isOpen) ControlPanelTab.INVENTORY.open()
        if (queryResults == null) return

        var list: MutableList<SpriteItem> = queryResults.asList()
        list = if (Random.nextBoolean()) snake(list) else topBottom(list)
        if (!shift || Keyboard.pressKey(KeyEvent.VK_SHIFT)) {
            list.stream().filter { item -> item.isValid && item.isVisible }.forEach { item -> item.interact("Drop") }
        }
        Keyboard.releaseKey(KeyEvent.VK_SHIFT)
    }

    private fun snake(list: MutableList<SpriteItem>): MutableList<SpriteItem> {
        var items = list
        val leftSide = ArrayList<SpriteItem>()
        val rightSide = ArrayList<SpriteItem>()
        items.forEach { item ->
            if (item.isValid) {
                if (item.index % 4 < 2) leftSide.add(item) else rightSide.add(item)
            }
        }

        items = ArrayList()
        items.addAll(leftSide)
        rightSide.reverse()
        items.addAll(rightSide)

        return items
    }


    private fun topBottom(list: MutableList<SpriteItem>): MutableList<SpriteItem> {
        var items = list
        val first = ArrayList<SpriteItem>()
        val second = ArrayList<SpriteItem>()
        val third = ArrayList<SpriteItem>()
        val fourth = ArrayList<SpriteItem>()
        items.forEach { item ->
            if (item.isValid) {
                when (item.index % 4) {
                    0 -> first.add(item)
                    1 -> second.add(item)
                    2 -> third.add(item)
                    3 -> fourth.add(item)
                }
            }
        }

        items = ArrayList()

        second.reverse() // down
        fourth.reverse() // down

        items.addAll(first)
        items.addAll(second)
        items.addAll(third)
        items.addAll(fourth)

        return items
    }
}