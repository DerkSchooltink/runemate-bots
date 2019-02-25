package bots.free.prifdinnas_cleansing_crystal

import bots.free.prifdinnas_cleansing_crystal.task.BuyCrystals
import bots.free.prifdinnas_cleansing_crystal.task.OfferCrystals
import bots.free.prifdinnas_cleansing_crystal.task.StopBot
import bots.free.prifdinnas_cleansing_crystal.ui.PrifdinnasCleansingCrystalUI
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class PrifdinnasCleansingCrystal : ExtendedTaskBot() {
    override fun onStart(vararg args: String) {
        embeddableUI = UIExtender(
            this,
            PrifdinnasCleansingCrystalUI(),
            "com/bots/free/prifdinnas_cleansing_crystal/ui/PrifdinnasCleansingCrystalUI.fxml"
        )
        add(BuyCrystals(), OfferCrystals(), StopBot())
    }
}
