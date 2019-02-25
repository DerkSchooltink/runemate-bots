package quantum.api.ui.controllers

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.script.Execution
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.core.LoopingThread
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

class AliasController(val bot: AbstractBot) : StackPane(), Initializable {

    @FXML
    private lateinit var mainLabel: Label

    @FXML
    private lateinit var activeAliasListView: ListView<String>

    @FXML
    private lateinit var aliasListView: ListView<String>

    @FXML
    private lateinit var moveButton: Button

    @FXML
    private lateinit var enableSwitchingRadioButton: RadioButton

    @FXML
    private lateinit var upperBoundSpinner: Spinner<Int>

    @FXML
    private lateinit var lowerBoundSpinner: Spinner<Int>

    private lateinit var aliases: List<String>
    private lateinit var currentAlias: String
    private lateinit var loop: LoopingThread

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        bot.platform.invokeAndWait {
            aliases = Environment.getAccountAliases().filter { it != Environment.getAccountAlias() }
            currentAlias = Environment.getAccountAlias()
        }

        aliasListView.items.addAll(aliases)
        activeAliasListView.items.add(currentAlias)

        aliasListView.selectionModel.selectionMode = SelectionMode.SINGLE
        activeAliasListView.selectionModel.selectionMode = SelectionMode.SINGLE

        aliasListView.setOnMouseClicked {
            activeAliasListView.selectionModel.let {
                if (it.selectedItem != null) it.clearSelection()
            }
        }

        activeAliasListView.setOnMouseClicked {
            aliasListView.selectionModel.let {
                if (it.selectedItem != null) it.clearSelection()
            }
        }

        moveButton.setOnAction {
            val selectedAlias = aliasListView.selectionModel.selectedItem
            val activeAlias = activeAliasListView.selectionModel.selectedItem

            when {
                selectedAlias != null -> activeAliasListView.items.add(selectedAlias).also { aliasListView.items.remove(selectedAlias) }
                activeAlias != null -> aliasListView.items.add(activeAlias).also { activeAliasListView.items.remove(activeAlias) }
            }
        }

        lowerBoundSpinner.setOnMouseClicked {
            if (upperBoundSpinner.value < lowerBoundSpinner.value) {
                upperBoundSpinner.valueFactory.value = lowerBoundSpinner.value
            }
        }

        upperBoundSpinner.setOnMouseClicked {
            if (lowerBoundSpinner.value > upperBoundSpinner.value) {
                lowerBoundSpinner.valueFactory.value = upperBoundSpinner.value
            }
        }

        enableSwitchingRadioButton.setOnAction {
            if (enableSwitchingRadioButton.isSelected) {
                var time = TimeUnit.MINUTES.toMillis(Random.nextInt(lowerBoundSpinner.value, upperBoundSpinner.value).toLong())
                bot.platform.invokeLater {
                    loop = LoopingThread({
                        val alias = activeAliasListView.items.filtered { it != currentAlias }.first()
                        bot.logger.info("Attempting to switch to $alias alias...")
                        bot.platform.invokeAndWait {
                            Environment.setAccount(alias)
                                    .also { logout() }
                                    .also { bot.logger.info("Successfully switched to $alias!") }
                                    .also { currentAlias = alias }
                                    .also { time = TimeUnit.MINUTES.toMillis(Random.nextInt(lowerBoundSpinner.value, upperBoundSpinner.value).toLong()) }
                        }
                    }, time.toInt()).also { it.start() }
                    bot.logger.info("Successfully started countdown for alias switching")
                }
            }
        }
    }

    private fun logout() = RuneScape.logout() && Execution.delayUntil({ !RuneScape.isLoggedIn() }, 600, 1200)
}