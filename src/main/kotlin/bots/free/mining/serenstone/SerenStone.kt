package bots.free.mining.serenstone

import bots.free.mining.serenstone.ui.SerenStoneController
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class SerenStone : ExtendedTaskBot() {
    override fun onStart(vararg args: String) {
        embeddableUI =
            UIExtender(this, SerenStoneController(this), "com/bots/free/mining/serenstone/ui/serenstone_ui.fxml")
        setLoopDelay(1000, 1500)
    }
}
