package bots.free.prifdinnas_cleansing_crystal

import bots.free.prifdinnas_cleansing_crystal.task.BuyCrystals
import bots.free.prifdinnas_cleansing_crystal.task.OfferCrystals
import bots.free.prifdinnas_cleansing_crystal.task.StopBot
import bots.free.prifdinnas_cleansing_crystal.ui.PrifdinnasCleansingCrystalUI
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class PrifdinnasCleansingCrystal : ExtendedTaskBot() {

    private val listenerManager = ListenerManager()
    private val breakHandler = BreakManager()

    override fun onStart(vararg args: String) {
        embeddableUI = UIExtender(this, PrifdinnasCleansingCrystalUI(), "com/bots/free/prifdinnas_cleansing_crystal/ui/PrifdinnasCleansingCrystalUI.fxml", listenerManager, breakHandler)
        add(BuyCrystals(), OfferCrystals(), StopBot())
    }
}
