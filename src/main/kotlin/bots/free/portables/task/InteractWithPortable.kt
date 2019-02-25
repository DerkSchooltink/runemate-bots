package bots.free.portables.task

import bots.free.portables.Portables
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.rs3.local.hud.interfaces.MakeXInterface
import com.runemate.game.api.script.Execution
import quantum.api.core.ExtendedTask

class InteractWithPortable : ExtendedTask<Portables>() {

    private var portable: GameObject? = null

    override fun validate(): Boolean {
        portable = GameObjects.newQuery().names(bot.portableSettings.portable.toString()).results().nearest()
        return portable != null && Players.getLocal()?.animationId == -1
                && bot.portableSettings.baseItem.isNotBlank() && Inventory.contains(bot.portableSettings.baseItem)
                && (bot.portableSettings.secondBaseItem.isBlank() || Inventory.contains(bot.portableSettings.secondBaseItem))
    }

    override fun execute() {
        if (Bank.isOpen()) Bank.close()
        if (!MakeXInterface.isOpen()) {
            if (portable?.interact(bot.portableSettings.action) == true) {
                Execution.delayUntil(MakeXInterface::isOpen, 300, 600)
            } else {
                portable?.interact(bot.portableSettings.action)
            }
        } else {
            if (Inventory.contains(bot.portableSettings.baseItem)) {
                if (MakeXInterface.getSelectedItem()?.name != bot.portableSettings.finishedProduct) {
                    MakeXInterface.selectItem(bot.portableSettings.finishedProduct)
                }
                if (MakeXInterface.confirm()) {
                    Execution.delayUntil({ !MakeXInterface.isOpen() && Players.getLocal()?.animationId == -1 && !Inventory.contains(bot.portableSettings.baseItem) }, { Players.getLocal()?.animationId != -1 }, 1800, 3600)
                }
            } else {
                MakeXInterface.close()
            }
        }
    }
}