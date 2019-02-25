package bots.free.mining.sandstone.ui

import bots.free.mining.sandstone.Sandstone
import bots.free.mining.sandstone.task.CatchButterfly
import bots.free.mining.sandstone.task.DrinkJuju
import bots.free.mining.sandstone.task.Drop
import bots.free.mining.sandstone.task.Mine
import quantum.api.core.ExtendedTask
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*


class SandstoneUI(private val bot: Sandstone) : StackPane(), Initializable {

    @FXML
    private lateinit var jujuCheckBox: CheckBox

    @FXML
    private lateinit var catchButterfliesCheckBox: CheckBox

    @FXML
    private lateinit var startButton: Button

    @FXML
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.onAction = startButtonAction()
    }

    @FXML
    private fun startButtonAction(): EventHandler<ActionEvent> {
        return EventHandler {
            startButton.text = "Update"
            bot.platform.invokeLater {
                bot.tasks.clear()

                when {
                    jujuCheckBox.isSelected -> bot.tasks.add(DrinkJuju())
                    catchButterfliesCheckBox.isSelected -> bot.tasks.add(CatchButterfly())
                }

                bot.tasks.addAll(Arrays.asList<ExtendedTask<Sandstone>>(Drop(), Mine()))

                bot.botSettings.isUsingJujuPotions = jujuCheckBox.isSelected
                bot.botSettings.isCatchingButterflies = catchButterfliesCheckBox.isSelected
            }
        }
    }

}
