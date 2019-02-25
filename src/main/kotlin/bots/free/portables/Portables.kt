package bots.free.portables

import bots.free.portables.settings.PortableSettings
import bots.free.portables.ui.PortableController
import com.runemate.game.api.hybrid.GameEvents
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class Portables : ExtendedTaskBot() {

    val portableSettings = PortableSettings()

    override fun onStart(vararg strings: String) {
        GameEvents.Universal.UNEXPECTED_ITEM_HANDLER.disable()
        setLoopDelay(1200, 1800)
        embeddableUI = UIExtender(this, PortableController(this), "com/bots/free/portables/ui/portables_ui.fxml")
    }
}