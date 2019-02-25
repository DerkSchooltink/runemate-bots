package bots.premium.divination.ui

import bots.premium.divination.task.*
import bots.premium.divination.HallOfMemories
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class HallOfMemoriesController(val bot: HallOfMemories) : StackPane(), Initializable {

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var twoTickCheckBox: CheckBox

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.onAction = startButtonAction()
    }

    private fun startButtonAction() = EventHandler<ActionEvent> {
        if (startButton.text != "Update") startButton.text = "Update"
        bot.platform.invokeLater {
            bot.settings.twoTick = twoTickCheckBox.isSelected
            bot.tasks.clear()
            bot.add(AddToPlinth(), DepositJars(), SiphonMemories(), GrabJars(), CaptureFragment(), InteractWithBud())
        }
    }
}