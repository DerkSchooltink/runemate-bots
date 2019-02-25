package bots.free.invention.ui

import bots.free.invention.InventionDisassemble
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class InventionUI(private val bot: InventionDisassemble) : StackPane(), Initializable {

    @FXML
    lateinit var destroyField: TextField

    @FXML
    lateinit var everythingCheckBox: CheckBox

    @FXML
    lateinit var startButton: Button

    @FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.setOnAction {
            when {
                !everythingCheckBox.isSelected -> bot.item = destroyField.text
                else -> bot.isDestroyingEverything = true
            }

            bot.start = true
            startButton.text = "Update"
        }

        destroyField.disableProperty().bind(everythingCheckBox.selectedProperty())
    }
}
