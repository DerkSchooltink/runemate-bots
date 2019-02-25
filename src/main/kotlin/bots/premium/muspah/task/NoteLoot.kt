package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.script.Execution

class NoteLoot : ExtendedTask<Muspah>() {

    override fun validate() = !Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
            && !Inventory.newQuery().names(*bot.settings.loot).unnoted().results().isEmpty()
            && Inventory.contains("Magic notepaper")

    override fun execute() {
        val magicNotepaper = Inventory.newQuery().names("Magic notepaper").results().first()
        Inventory.newQuery().names(*bot.settings.loot).unnoted().filter { it.definition?.stacks() == false }.results().first()?.let {
            if (Inventory.getSelectedItem() == magicNotepaper) {
                if (it.click()) {
                    Execution.delayUntil({ !it.isValid }, 600, 1200)
                }
            } else if (magicNotepaper?.interact("Use") == true && it.click()) {
                logger.info("Noting loot...")
                Execution.delayUntil({ !it.isValid }, 600, 1200)
            }
        }
    }
}