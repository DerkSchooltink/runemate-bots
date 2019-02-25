package bots.free.summoning.ui

import bots.free.summoning.Summoning
import bots.free.summoning.data.Obelisk
import bots.free.summoning.tasks.InfusePouches
import bots.free.summoning.tasks.WalkToBank
import bots.free.summoning.tasks.WalkToObelisk
import bots.free.summoning.tasks.WithdrawPreset
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class SummoningController(private val bot: Summoning) : StackPane(), Initializable {

    @FXML
    private lateinit var obeliskComboBox: ComboBox<Obelisk>

    @FXML
    private lateinit var pouchComboBox: ComboBox<com.runemate.game.api.rs3.local.hud.interfaces.Summoning.Familiar>

    @FXML
    private lateinit var presetComboBox: ComboBox<Int>

    @FXML
    private lateinit var startButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.onAction = startButtonAction()

        presetComboBox.items.addAll(1..10).also { presetComboBox.selectionModel.selectFirst() }
        obeliskComboBox.items.addAll(Obelisk.values()).also { obeliskComboBox.selectionModel.selectFirst() }
        pouchComboBox.items.addAll(com.runemate.game.api.rs3.local.hud.interfaces.Summoning.Familiar.values()).also { pouchComboBox.selectionModel.selectFirst() }
    }

    private fun startButtonAction() = EventHandler<ActionEvent> {
        startButton.text = "Update"
        bot.platform.invokeLater {
            bot.tasks.clear()
            bot.tasks.addAll(Arrays.asList(WalkToObelisk(), WalkToBank(), InfusePouches(), WithdrawPreset()))

            bot.botSettings.obelisk = obeliskComboBox.selectionModel.selectedItem
            bot.botSettings.ingredients = pouchComboBox.selectionModel.selectedItem.ingredients?.map { it.toString() }.orEmpty().toTypedArray()
            bot.botSettings.charm = pouchComboBox.selectionModel.selectedItem.charm.name.toLowerCase().capitalize()
            bot.botSettings.preset = presetComboBox.value.toInt()
            bot.botSettings.pouchName = pouchComboBox.selectionModel.selectedItem.pouch.toString()
        }
    }
}
