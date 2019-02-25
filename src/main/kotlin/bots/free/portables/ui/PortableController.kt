package bots.free.portables.ui

import bots.free.portables.Portables
import bots.free.portables.data.Portable
import bots.free.portables.task.InteractWithPortable
import bots.free.portables.task.WithdrawPreset
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import java.net.URL
import java.util.*

class PortableController(private val bot: Portables) : Initializable, VBox() {

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var portableTypeComboBox: ComboBox<Portable>

    @FXML
    private lateinit var baseItemTextField: TextField

    @FXML
    private lateinit var finishedProductTextField: TextField

    @FXML
    private lateinit var secondBaseItemTextField: TextField

    @FXML
    private lateinit var actionComboBox: ComboBox<String>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        portableTypeComboBox.items.addAll(Portable.values())

        portableTypeComboBox.setOnAction {
            finishedProductTextField.isDisable = Portable.noProductPortables.contains(portableTypeComboBox.selectionModel.selectedItem)
        }

        bot.platform.invokeLater {
            bot.settings.let {
                if (it["portable"] == null) {
                    portableTypeComboBox.selectionModel.selectFirst()
                } else {
                    portableTypeComboBox.selectionModel.select(Integer.parseInt(it["portable"] as String))
                }

                if (it["item"] != null) baseItemTextField.text = it["item"] as String
                if (it["second_item"] != null) secondBaseItemTextField.text = it["second_item"] as String
                if (it["product"] != null) finishedProductTextField.text = it["product"] as String
            }
        }

        portableTypeComboBox.selectionModel.selectedItemProperty().addListener { _, _, newPortable ->
            actionComboBox.items.clear()
            actionComboBox.items.addAll(newPortable.actions)
            actionComboBox.selectionModel.selectFirst()
        }

        startButton.setOnAction {
            bot.tasks.clear()
            bot.add(InteractWithPortable(), WithdrawPreset())

            bot.settings.setProperty("portable", portableTypeComboBox.selectionModel.selectedIndex.toString())
            bot.settings.setProperty("item", baseItemTextField.text)
            bot.settings.setProperty("second_item", secondBaseItemTextField.text)
            bot.settings.setProperty("product", finishedProductTextField.text)

            bot.portableSettings.portable = portableTypeComboBox.selectionModel.selectedItem
            bot.portableSettings.baseItem = baseItemTextField.text
            bot.portableSettings.secondBaseItem = secondBaseItemTextField.text
            bot.portableSettings.finishedProduct = finishedProductTextField.text
            bot.portableSettings.action = actionComboBox.selectionModel.selectedItem

            startButton.text = "Update"
        }
    }
}