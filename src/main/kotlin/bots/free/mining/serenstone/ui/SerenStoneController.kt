package bots.free.mining.serenstone.ui

import bots.free.mining.serenstone.SerenStone
import bots.free.mining.serenstone.task.Mine
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class SerenStoneController(val bot: SerenStone) : Initializable, StackPane() {

    @FXML
    private lateinit var startButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.setOnAction {
            bot.platform.invokeLater {
                bot.tasks.run {
                    clear()
                    add(Mine())
                }
            }
            startButton.text = "Update"
        }
    }
}