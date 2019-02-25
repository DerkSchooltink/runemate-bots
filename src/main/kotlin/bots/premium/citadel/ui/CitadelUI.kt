package bots.premium.citadel.ui

import bots.premium.citadel.task.*
import bots.premium.citadel.Citadel
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class CitadelUI(private val bot: Citadel) : StackPane(), Initializable {

    @FXML
    private lateinit var woodcuttingRadioButton: RadioButton

    @FXML
    private lateinit var cookingRadioButton: RadioButton

    @FXML
    private lateinit var preciousOreRadioButton: RadioButton

    @FXML
    private lateinit var oreRadioButton: RadioButton

    @FXML
    private lateinit var summoningRadioButton: RadioButton

    @FXML
    private lateinit var oreRockRadioButton: RadioButton

    @FXML
    private lateinit var preciousOreMiningRadioButton: RadioButton

    @FXML
    private lateinit var stoneOreRadioButton: RadioButton

    @FXML
    private lateinit var weaveRadioButton: RadioButton

    @FXML
    private lateinit var firemakingRadioButton: RadioButton

    @FXML
    private lateinit var anagogicOrtCheckBox: CheckBox

    @FXML
    private lateinit var startButton: Button

    private fun startButtonAction() = EventHandler<ActionEvent> {
        startButton.text = "Update"
        bot.platform.invokeLater { bot.setSettings(addTasks(), anagogicOrtCheckBox.isSelected) }
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        ToggleGroup().apply {
            woodcuttingRadioButton.toggleGroup = this
            cookingRadioButton.toggleGroup = this
            oreRadioButton.toggleGroup = this
            preciousOreRadioButton.toggleGroup = this
            summoningRadioButton.toggleGroup = this
            oreRockRadioButton.toggleGroup = this
            preciousOreMiningRadioButton.toggleGroup = this
            stoneOreRadioButton.toggleGroup = this
            weaveRadioButton.toggleGroup = this
        }

        startButton.onAction = startButtonAction()
    }


    private fun addTasks() = when {
        woodcuttingRadioButton.isSelected -> Woodcutting()
        cookingRadioButton.isSelected -> Cooking()
        oreRadioButton.isSelected -> OreSmithing()
        preciousOreRadioButton.isSelected -> PreciousOreSmithing()
        summoningRadioButton.isSelected -> Summoning()
        stoneOreRadioButton.isSelected -> Mining("Stone")
        preciousOreMiningRadioButton.isSelected -> Mining("Precious ore")
        oreRockRadioButton.isSelected -> Mining("Ore")
        weaveRadioButton.isSelected -> WeaveCrafting()
        firemakingRadioButton.isSelected -> Kiln()
        else -> null
    }
}
