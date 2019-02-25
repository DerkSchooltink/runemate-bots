package bots.private_bots.catacombs.ui

import bots.private_bots.catacombs.CatacombsSlayer
import bots.private_bots.catacombs.data.CatacombsMonster
import bots.private_bots.catacombs.tasks.*
import com.runemate.game.api.hybrid.util.io.ManagedProperties
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.layout.StackPane
import quantum.api.framework.extensions.setSelection
import quantum.api.game.data.AttackPotion
import quantum.api.game.data.Food
import quantum.api.game.data.Potion
import quantum.api.game.data.StrengthPotion
import java.net.URL
import java.util.*

class CatacombsSlayerUI(val bot: CatacombsSlayer) : StackPane(), Initializable {

    private lateinit var managedProperties: ManagedProperties

    @FXML
    private lateinit var specialAttackCheckBox: CheckBox

    @FXML
    private lateinit var startButton: Button

    @FXML
    private lateinit var monsterComboBox: ComboBox<CatacombsMonster>

    @FXML
    private lateinit var foodComboBox: ComboBox<Food>

    @FXML
    private lateinit var attackPotionComboBox: ComboBox<Potion>

    @FXML
    private lateinit var strengthPotionComboBox: ComboBox<Potion>

    @FXML
    private lateinit var foodAmountSpinner: Spinner<Int>

    @FXML
    private lateinit var strengthPotionAmountSpinner: Spinner<Int>

    @FXML
    private lateinit var attackPotionAmountSpinner: Spinner<Int>

    @FXML
    private lateinit var lootStackPriceSpinner: Spinner<Int>

    @FXML
    private lateinit var lootPriceSpinner: Spinner<Int>

    @FXML
    private lateinit var specialAttackPercentageSpinner: Spinner<Int>

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        bot.platform.invokeAndWait { managedProperties = bot.settings }

        specialAttackPercentageSpinner.disableProperty().bind(!specialAttackCheckBox.selectedProperty())

        managedProperties.run {
            monsterComboBox.items.setAll(*CatacombsMonster.values())
            monsterComboBox.setSelection(getProperty("monster"))

            foodComboBox.items.setAll(*Food.values())
            foodComboBox.setSelection(getProperty("food"))

            strengthPotionComboBox.items.setAll(*StrengthPotion.values())
            strengthPotionComboBox.setSelection(getProperty("strength"))

            attackPotionComboBox.items.setAll(*AttackPotion.values())
            attackPotionComboBox.setSelection(getProperty("attack"))

            lootPriceSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1)
            lootStackPriceSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1)
            strengthPotionAmountSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 28, 0, 1)
            attackPotionAmountSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 28, 0, 1)
            foodAmountSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 28, 0, 1)
            specialAttackPercentageSpinner.valueFactory = SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 25)

            lootPriceSpinner.setSelection(getProperty("loot_price"))
            lootStackPriceSpinner.setSelection(getProperty("loot_stack_price"))
            specialAttackPercentageSpinner.setSelection(getProperty("special_attack_percentage"))
            strengthPotionAmountSpinner.setSelection(getProperty("strength_amount"))
            attackPotionAmountSpinner.setSelection(getProperty("attack_amount"))
            foodAmountSpinner.setSelection(getProperty("food_amount"))

            specialAttackCheckBox.setSelection(getProperty("use_special_attack"))
        }

        startButton.setOnAction {

            startButton.text = "Update"

            bot.run {
                settings.run {
                    setProperty("monster", monsterComboBox.selectionModel.selectedIndex.toString())
                    setProperty("food", foodComboBox.selectionModel.selectedIndex.toString())
                    setProperty("strength", strengthPotionComboBox.selectionModel.selectedIndex.toString())
                    setProperty("attack", attackPotionComboBox.selectionModel.selectedIndex.toString())
                    setProperty("loot_price", lootPriceSpinner.value.toString())
                    setProperty("loot_stack_price", lootStackPriceSpinner.value.toString())
                    setProperty("strength_amount", strengthPotionAmountSpinner.value.toString())
                    setProperty("attack_amount", attackPotionAmountSpinner.value.toString())
                    setProperty("food_amount", foodAmountSpinner.value.toString())
                    setProperty("special_attack_percentage", specialAttackPercentageSpinner.value.toString())
                    setProperty("use_special_attack", specialAttackCheckBox.isSelected.toString())
                }

                platform.invokeLater {
                    tasks.clear()
                    add(AttackMonster(), PerformSpecialAttack(), LootItem(), EatFood(), CloseBank(), CombinePieces(), PotionHandler(), DepositItems(), OpenBank(), WalkToBank(), WalkToMonster(), WithdrawItems())
                    bot.botSettings.clearLootLists()
                }

                bot.botSettings.apply {
                    strengthPotion = strengthPotionComboBox.selectionModel.selectedItem
                    attackPotion = attackPotionComboBox.selectionModel.selectedItem

                    attackPotionAmount = attackPotionAmountSpinner.value
                    strengthPotionAmount = strengthPotionAmountSpinner.value
                    foodAmount = foodAmountSpinner.value

                    lootStackPrice = lootStackPriceSpinner.value
                    lootPrice = lootPriceSpinner.value

                    monster = monsterComboBox.selectionModel.selectedItem
                    food = foodComboBox.selectionModel.selectedItem
                }
            }
        }
    }
}
