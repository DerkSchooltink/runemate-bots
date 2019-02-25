package bots.premium.ascension

import bots.premium.ascension.tasks.*
import bots.premium.ascension.settings.AscensionSettings
import bots.premium.ascension.ui.AscensionController
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager

class Ascension : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val ascensionSettings = AscensionSettings()

    private val breakHandler = BreakManager()

    override fun onStart(vararg args: String) {
        QuantumPlayerSense.initialize()
        embeddableUI = UIExtender(this, AscensionController(this), "com/bots/premium/ascension/ui/ascension_ui.fxml", listenerManager, breakHandler)
        setLoopDelay(1000, 1500)
    }

    fun tasks() = arrayOf(
            TeleportAway(),
            PotionHandler(),
            Attack(),
            DeactivatePrayer(),
            ActivatePrayer(),
            DrinkPrayerPotion(),
            Loot(),
            NoteKey(),
            OpenBank(),
            WithdrawPreset(),
            WalkToBank(),
            WalkToMonsters(),
            Eat(),
            Disassemble(),
            ApplyWeaponPoison()
    )
}