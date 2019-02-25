package bots.free.summoning.tasks

import bots.free.summoning.Summoning
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.rs3.local.hud.interfaces.MakeXInterface
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class InfusePouches : ExtendedTask<Summoning>() {

    private var obelisk: GameObject? = null

    override fun validate(): Boolean {
        obelisk = GameObjects.newQuery().names("Obelisk").actions("Infuse-pouch").results().nearest()
        return obelisk != null && !Bank.isOpen() && Inventory.containsAllOf(*bot.botSettings.ingredients)
    }

    override fun execute() {
        if (MakeXInterface.isOpen()) {
            val charm = "${bot.botSettings.charm} Charm Pouches"
            if (MakeXInterface.isSelectedCategory(charm)) {
                val item = bot.botSettings.pouchName
                if (MakeXInterface.isSelectedItem(item)) {
                    MakeXInterface.confirm()
                } else {
                    MakeXInterface.selectItem(item)
                }
            } else {
                MakeXInterface.selectCategory(charm)
            }
        } else {
            if (obelisk?.interact("Infuse-pouch") == true) {
                Execution.delayUntil({ Inventory.contains(Pattern.compile(".*pouch.*", Pattern.CASE_INSENSITIVE)) }, 600, 1200)
            }
        }
    }
}