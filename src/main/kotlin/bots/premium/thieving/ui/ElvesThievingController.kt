package bots.premium.thieving.ui

import bots.premium.thieving.ElvesThieving
import bots.premium.thieving.data.IntermediateTask
import bots.premium.thieving.task.*
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class ElvesThievingController(val bot: ElvesThieving) : StackPane(), Initializable {

    @FXML
    private lateinit var intermediateTaskComboBox: ComboBox<IntermediateTask>

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var crystalMaskCheckBox: CheckBox

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        intermediateTaskComboBox.items.setAll(*IntermediateTask.values())
        intermediateTaskComboBox.selectionModel.selectFirst()

        startButton.setOnAction {
            bot.platform.invokeLater {

                bot.settings.run {
                    intermediateTask = intermediateTaskComboBox.selectionModel.selectedItem
                    useCrystalMask = crystalMaskCheckBox.isSelected
                }

                bot.tasks.run {
                    clear()
                    addAll(arrayOf(TraverseToClan(), Pickpocket(), IntermediateHandling(), BankHandling(), CastCrystalMask()))
                }
            }
            startButton.text = "Update"
        }
    }
}