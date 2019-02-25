package bots.free.prifdinnas_bonfire.ui

import bots.free.prifdinnas_bonfire.PrifdinnasBonfire
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Slider
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class PrifdinnasBonfireUI(private val bot: PrifdinnasBonfire) : StackPane(), Initializable {

    @FXML
    lateinit var presetSlider: Slider

    @FXML
    lateinit var startButton: Button

    @FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.setOnAction {
            startButton.text = "Update"
            bot.setTasks()
            bot.preset = presetSlider.value.toInt()
        }
    }
}
