package bots.premium.ascension

import bots.premium.ascension.settings.AscensionSettings
import bots.premium.ascension.tasks.*
import bots.premium.ascension.ui.AscensionController
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense

class Ascension : ExtendedTaskBot() {

    val ascensionSettings = AscensionSettings()

    override fun onStart(vararg args: String) {
        QuantumPlayerSense.initialize()
        embeddableUI = UIExtender(this, AscensionController(this), "com/bots/premium/ascension/ui/ascension_ui.fxml")
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