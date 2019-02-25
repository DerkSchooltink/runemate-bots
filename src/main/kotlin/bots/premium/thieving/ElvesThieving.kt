package bots.premium.thieving

import bots.premium.thieving.settings.ElvesThievingSettings
import bots.premium.thieving.ui.ElvesThievingController
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class ElvesThieving : ExtendedTaskBot() {

    val settings = ElvesThievingSettings()

    override fun onStart(vararg arguments: String) {
        embeddableUI =
            UIExtender(this, ElvesThievingController(this), "com/bots/premium/thieving/ui/elves_thieving_ui.fxml")
    }
}