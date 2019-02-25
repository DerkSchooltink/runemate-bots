package bots.free.prifdinnas_harps.ui

import bots.free.prifdinnas_harps.PrifdinnasHarps
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.script.framework.AbstractBot
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class PrifdinnasHarpsUI<T : AbstractBot>(bot: T) : StackPane(), Initializable {

    private val bot: PrifdinnasHarps = bot as PrifdinnasHarps

    @FXML
    lateinit var offsetCombobox: ComboBox<Int>

    @FXML
    lateinit var locationBox: ComboBox<String>

    @FXML
    lateinit var sleeperCheckbox: CheckBox

    @FXML
    lateinit var anagogicOrtCheckBox: CheckBox

    @FXML
    lateinit var startButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.onAction = setSettings()
        anagogicOrtCheckBox.isSelected = false
        offsetCombobox.items?.addAll(10, 20, 30, 40, 50, 60, 70, 80, 90)
        locationBox.items?.addAll("North", "East", "South", "West")
        locationBox.selectionModel?.selectFirst()
        offsetCombobox.selectionModel?.selectFirst()
        sleeperCheckbox.isSelected = false
    }

    private fun setSettings(): EventHandler<ActionEvent> {
        return EventHandler {
            when (locationBox.selectionModel.selectedItem) {
                "North" -> bot.coordinate = Coordinate(2132, 3338, 1)
                "East" -> bot.coordinate = Coordinate(2135, 3335, 1)
                "South" -> bot.coordinate = Coordinate(2132, 3332, 1)
                "West" -> bot.coordinate = Coordinate(2129, 3335, 1)
            }
            bot.percentage = offsetCombobox.selectionModel?.selectedItem
            bot.isSleeper = sleeperCheckbox.isSelected
            bot.isLootingOrt = anagogicOrtCheckBox.isSelected

            startButton.text = "Update"
        }
    }
}
