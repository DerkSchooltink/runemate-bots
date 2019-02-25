package bots.premium.woodcutting

import bots.premium.woodcutting.settings.WoodcuttingSettings
import bots.premium.woodcutting.ui.WoodCuttingUI
import com.google.gson.Gson
import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.util.io.ManagedProperties
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.game.GameMode.OSRS
import quantum.api.game.GameMode.RS3

class WoodCutting : ExtendedTaskBot() {

    val settings = WoodcuttingSettings()

    override fun onStart(vararg arguments: String?) {
        applySettings(getSettings(), settings)
        embeddableUI = UIExtender(
                bot = this,
                controller = WoodCuttingUI(this, if (Environment.isRS3()) RS3 else OSRS),
            fxmlPath = "com/bots/premium/woodcutting/ui/woodcutting_ui.fxml"
        )
    }

    private fun applySettings(managedProperties: ManagedProperties, settings: WoodcuttingSettings): WoodcuttingSettings {
        val woodcuttingSettings = managedProperties["woodcutting_settings"] as? String ?: return settings
        val propertySettings = Gson().fromJson(woodcuttingSettings, WoodcuttingSettings::class.java)
        return settings.apply {
            chopLocation.value = propertySettings.chopLocation.value
            bankingLocation.value = propertySettings.bankingLocation.value
            tree.value = propertySettings.tree.value

            isPowerchopping.value = propertySettings.isPowerchopping.value
            isLootingItems.value = propertySettings.isLootingItems.value
            isUsingJujuPotion.value = propertySettings.isUsingJujuPotion.value
            isBurningLogs.value = propertySettings.isBurningLogs.value
            isUsingClosestBank.value = propertySettings.isUsingClosestBank.value
        }
    }
}