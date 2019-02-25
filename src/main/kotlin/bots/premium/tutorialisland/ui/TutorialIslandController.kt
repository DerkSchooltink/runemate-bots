package bots.premium.tutorialisland.ui

import bots.premium.tutorialisland.task.*
import bots.premium.tutorialisland.TutorialIsland
import com.runemate.game.api.hybrid.Environment
import javafx.collections.FXCollections
import javafx.collections.ObservableSet
import javafx.collections.SetChangeListener
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class TutorialIslandController(val bot: TutorialIsland) : StackPane(), Initializable {

    val handledAliases: ObservableSet<String> = FXCollections.observableSet<String>()
    val activeAliases: ObservableSet<String> = FXCollections.observableSet<String>()
    private val allAliases: ObservableSet<String> = FXCollections.observableSet<String>()!!

    @FXML
    private lateinit var aliasListView: ListView<String>

    @FXML
    private lateinit var moveToActiveButton: Button

    @FXML
    private lateinit var moveToInactiveButton: Button

    @FXML
    private lateinit var activeAliasListView: ListView<String>

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var finishedListView: ListView<String>

    private fun getCurrentAlias(): String {
        var test = ""
        bot.platform.invokeAndWait {
            test = Environment.getAccountAlias()
        }
        return test
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        startButton.onAction = startButtonAction()

        finishedListView.bind(handledAliases)
        activeAliasListView.bind(activeAliases)
        aliasListView.bind(allAliases)

        bot.platform.invokeLater {
            allAliases.addAll(Environment.getAccountAliases().filter { it != getCurrentAlias() })
        }

        activeAliases.add(getCurrentAlias())

        moveToActiveButton.setOnAction {
            aliasListView.selectionModel.selectedItem?.let {
                activeAliases.add(it)
                allAliases.remove(it)
                aliasListView.selectionModel.selectFirst()
            }
        }

        moveToInactiveButton.setOnAction {
            activeAliasListView.selectionModel.selectedItem?.let {
                if (it != getCurrentAlias()) {
                    allAliases.add(it)
                    activeAliases.remove(it)
                    activeAliasListView.selectionModel.selectFirst()
                }
            }
        }
    }

    private fun startButtonAction(): EventHandler<ActionEvent> {
        return EventHandler {
            startButton.text = "Update"
            activeAliases.addAll(activeAliasListView.items)
            bot.tasks.run {
                clear()
                addAll(arrayOf(
                    BankHandler(), ChangeAlias(), CookingHandler(), FightingHandler(), MiningHandler(),
                        PriestHandler(), QuestHandler(), RuneScapeGuideHandler(), SurvivalHandler(), WizardHandler()
                ))
            }
        }
    }
}

private fun <T> ListView<T>.bind(set: ObservableSet<T>) {
    set.addListener { change: SetChangeListener.Change<out T> ->
        when {
            change.wasAdded() -> items.add(change.elementAdded)
            change.wasRemoved() -> items.remove(change.elementRemoved)
        }
    }
}
