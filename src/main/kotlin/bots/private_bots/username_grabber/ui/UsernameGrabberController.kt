package bots.private_bots.username_grabber.ui

import bots.private_bots.username_grabber.UsernameGrabber
import bots.private_bots.username_grabber.task.GatherUsernames
import bots.private_bots.username_grabber.task.HopWorld
import com.runemate.game.api.script.framework.AbstractBot
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.RadioButton
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class UsernameGrabberController<T : AbstractBot>(bot: T) : StackPane(), Initializable {

    private val bot: UsernameGrabber = bot as UsernameGrabber

    @FXML
    lateinit var usernameListView: ListView<String>

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var p2pRadioButton: RadioButton

    @FXML
    private lateinit var f2pRadioButton: RadioButton

    @FXML
    private lateinit var noHoppingRadioButton: RadioButton

    override fun initialize(location: URL, resources: ResourceBundle) {
        ToggleGroup().toggles.setAll(p2pRadioButton, f2pRadioButton, noHoppingRadioButton)

        startButton.onAction = startButtonAction()
        p2pRadioButton.isSelected = true
    }

    @FXML
    private fun startButtonAction() = EventHandler<ActionEvent> {
        startButton.text = "Update"
        bot.platform.invokeLater {
            bot.tasks.clear()

            if (!noHoppingRadioButton.isSelected) {
                bot.setMember(p2pRadioButton.isSelected)
                bot.add(HopWorld())
            } else {
                bot.isHopWorlds = true
            }

            bot.add(GatherUsernames())
        }
    }
}
