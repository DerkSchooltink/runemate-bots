package bots.premium.ascension.tasks

import bots.premium.ascension.Ascension
import quantum.api.core.ExtendedTask
import quantum.api.game.Interaction
import quantum.api.game.useOn
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem

class NoteKey : ExtendedTask<Ascension>() {

    private val loot = arrayOf("Ascension Keystone Tertius", "Ascension Keystone Sextus", "Ascension Keystone Secundus", "Ascension Keystone Quintus", "Ascension Keystone Quartus", "Ascension Keystone Primus", "Ascension grips")
    private var itemToNote: SpriteItem? = null
    private var notes: SpriteItem? = null

    override fun validate(): Boolean {
        if (!bot.ascensionSettings.isUsingMagicNotepaper) {
            return false
        }

        itemToNote = Inventory.newQuery().names(*loot).filter { x ->
            val definition = x.definition
            definition != null && !definition.isNoted
        }.results().first()

        notes = Inventory.newQuery().names("Magic notepaper").results().first()

        return itemToNote != null && notes != null
    }

    override fun execute() {
        logger.info("Noting key...")
        if (Inventory.getSelectedItem() != null && !Inventory.getSelectedItem()?.definition?.name.equals("Magic notepaper")) {
            Interaction.deselectItem()
        } else {
            notes.useOn(itemToNote)
        }
    }
}
