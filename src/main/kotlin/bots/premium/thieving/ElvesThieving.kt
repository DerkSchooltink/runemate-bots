package bots.premium.thieving

import bots.premium.thieving.settings.ElvesThievingSettings
import bots.premium.thieving.ui.ElvesThievingController
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class ElvesThieving : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val settings = ElvesThievingSettings()

    override fun onStart(vararg arguments: String) {
        embeddableUI = UIExtender(this, ElvesThievingController(this), "com/bots/premium/thieving/ui/elves_thieving_ui.fxml", listenerManager, breakHandler)
    }
}