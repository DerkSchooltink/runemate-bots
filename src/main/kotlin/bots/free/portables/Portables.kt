package bots.free.portables

import bots.free.portables.settings.PortableSettings
import bots.free.portables.ui.PortableController
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
import com.runemate.game.api.hybrid.GameEvents
import quantum.api.core.ExtendedTaskBot

class Portables : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val portableSettings = PortableSettings()

    override fun onStart(vararg strings: String) {
        GameEvents.Universal.UNEXPECTED_ITEM_HANDLER.disable()
        setLoopDelay(1200, 1800)
        embeddableUI = UIExtender(this, PortableController(this), "com/bots/free/portables/ui/portables_ui.fxml", listenerManager, breakHandler)
    }
}