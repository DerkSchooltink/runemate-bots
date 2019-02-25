package bots.premium.woodcutting.ui

import bots.premium.woodcutting.WoodCutting
import bots.premium.woodcutting.data.BankingLocation
import bots.premium.woodcutting.data.ChopLocation
import bots.premium.woodcutting.data.Tree
import bots.premium.woodcutting.task.*
import bots.premium.woodcutting.task.elder.ChopElderTree
import bots.premium.woodcutting.task.elder.TraverseToNewTree
import bots.premium.woodcutting.task.firemaking.BurnLogs
import com.google.gson.Gson
import quantum.api.game.GameMode
import quantum.api.game.GameMode.RS3
import com.runemate.game.api.script.framework.task.Task
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.Control
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class WoodCuttingUI(private val bot: WoodCutting, private val gameMode: GameMode) : StackPane(), Initializable {

    @FXML
    private lateinit var chopLocationComboBox: ComboBox<ChopLocation>

    @FXML
    private lateinit var treeTypeComboBox: ComboBox<Tree>

    @FXML
    private lateinit var bankLocationComboBox: ComboBox<BankingLocation>

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var burnLogsCheckBox: CheckBox

    @FXML
    private lateinit var lootNestsCheckBox: CheckBox

    @FXML
    private lateinit var powerchoppingCheckBox: CheckBox

    @FXML
    private lateinit var jujuCheckBox: CheckBox

    @FXML
    private lateinit var useClosestBankCheckBox: CheckBox

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        if (gameMode == RS3) enableComponents(burnLogsCheckBox, jujuCheckBox)

        useClosestBankCheckBox.selectedProperty().bindBidirectional(bankLocationComboBox.disableProperty())
        powerchoppingCheckBox.selectedProperty().addListener { _, _, isPowerchopping -> powerChoppingCheckBoxListener(isPowerchopping) }
        chopLocationComboBox.valueProperty().addListener { _, _, selectedChopLocation -> selectedChopLocationListener(selectedChopLocation) }
        useClosestBankCheckBox.selectedProperty().bindBidirectional(bot.settings.isUsingClosestBank)

        bankLocationComboBox.items.setAll(BankingLocation.values().sortedBy { it.toString() })
        chopLocationComboBox.items.setAll(ChopLocation.values().filter { it.gameMode.contains(gameMode) }.sortedBy { it.name })

        startButton.onAction = startButtonAction()

        chopLocationComboBox.valueProperty().bindBidirectional(bot.settings.chopLocation)
        bankLocationComboBox.valueProperty().bindBidirectional(bot.settings.bankingLocation)
        treeTypeComboBox.valueProperty().bindBidirectional(bot.settings.tree)

        powerchoppingCheckBox.selectedProperty().bindBidirectional(bot.settings.isPowerchopping)
        jujuCheckBox.selectedProperty().bindBidirectional(bot.settings.isUsingJujuPotion)
        burnLogsCheckBox.selectedProperty().bindBidirectional(bot.settings.isBurningLogs)
        lootNestsCheckBox.selectedProperty().bindBidirectional(bot.settings.isLootingItems)
    }

    private fun getTasks() = mutableListOf<Task>().apply {
        add(BreakingTask())
        if (powerchoppingCheckBox.isSelected) {
            addAll(arrayListOf(Drop(), Destroy()))
            if (treeTypeComboBox.selectionModel.selectedItem === Tree.IVY) {
                add(LootItems())
            }
        } else {
            addAll(arrayListOf(WalkToBank(), Banking(), OpenBank(), CloseBank()))
            if (lootNestsCheckBox.isSelected) {
                add(LootItems())
            }
        }
        if (treeTypeComboBox.selectionModel.selectedItem == Tree.ELDER) {
            addAll(arrayListOf(ChopElderTree(), TraverseToNewTree(), DragonAxeSpecialAttack(), Unselect(), OpenInventory()))
        } else {
            addAll(arrayListOf(Chop(), WalkToTrees(), DragonAxeSpecialAttack(), Unselect(), OpenInventory()))
        }
        when {
            jujuCheckBox.isSelected -> add(DrinkJujuPotion())
            burnLogsCheckBox.isSelected -> add(BurnLogs())
        }
    }.toTypedArray()

    private fun startButtonAction() = EventHandler<ActionEvent> {
        startButton.text = "Update"
        bot.getSettings().setProperty("woodcutting_settings", Gson().toJson(bot.settings))
        bot.platform.invokeLater {
            bot.tasks.clear()
            bot.add(*getTasks())
        }
    }

    private fun powerChoppingCheckBoxListener(isPowerchopping: Boolean) {
        if (isPowerchopping) {
            treeTypeComboBox.items.setAll(*Tree.values())
            disableComponents(bankLocationComboBox, chopLocationComboBox, lootNestsCheckBox, burnLogsCheckBox, jujuCheckBox, useClosestBankCheckBox)
        } else {
            enableComponents(bankLocationComboBox, chopLocationComboBox, lootNestsCheckBox, burnLogsCheckBox, jujuCheckBox, useClosestBankCheckBox)
        }
        treeTypeComboBox.selectionModel.selectFirst()
    }

    private fun selectedChopLocationListener(selectedChopLocation: ChopLocation?) = selectedChopLocation?.run {
        treeTypeComboBox.items.setAll(selectedChopLocation.map.keys)
        treeTypeComboBox.selectionModel.selectFirst()
        bankLocationComboBox.selectionModel.select(selectedChopLocation.firstBankLocation)
    }

    private fun enableComponents(vararg components: Control) = Arrays.stream(components).forEach { it.isDisable = false }
    private fun disableComponents(vararg components: Control) = Arrays.stream(components).forEach { it.isDisable = true }
}
