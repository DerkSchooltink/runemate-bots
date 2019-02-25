package bots.premium.ascension.ui

import bots.premium.ascension.Ascension
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.layout.StackPane
import quantum.api.game.data.*
import java.net.URL
import java.util.*

class AscensionController(private val bot: Ascension) : StackPane(), Initializable {

    @FXML
    private lateinit var useInventionCheckBox: CheckBox

    @FXML
    private lateinit var prayerPotionComboBox: ComboBox<PrayerPotion>

    @FXML
    private lateinit var presetComboBox: ComboBox<Int>

    @FXML
    private lateinit var useFoodCheckBox: CheckBox

    @FXML
    private lateinit var foodComboBox: ComboBox<Food>

    @FXML
    private lateinit var usePrayerPotionCheckBox: CheckBox

    @FXML
    private lateinit var magicNotepaperCheckBox: CheckBox

    @FXML
    private lateinit var potionComboBox: ComboBox<Potion>

    @FXML
    private lateinit var usePotionCheckBox: CheckBox

    @FXML
    private lateinit var useWeaponPoisonCheckBox: CheckBox

    @FXML
    private lateinit var weaponPoisonComboBox: ComboBox<WeaponPoison>

    @FXML
    private lateinit var startButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        foodComboBox.items.setAll(*Food.values())
        foodComboBox.selectionModel.selectFirst()

        useFoodCheckBox.onAction = useFoodCheckBoxAction()

        prayerPotionComboBox.items.setAll(*PrayerPotion.values())
        prayerPotionComboBox.selectionModel.selectFirst()

        usePrayerPotionCheckBox.onAction = usePrayerCheckBoxAction()

        useFoodCheckBox.onAction = useFoodCheckBoxAction()

        presetComboBox.items.setAll(1, 2, 3, 4, 5, 6, 7, 8)
        presetComboBox.selectionModel.selectFirst()

        potionComboBox.items.setAll(*Overload.values(), *RangingPotion.values())
        potionComboBox.selectionModel.selectFirst()

        usePotionCheckBox.onAction = usePotionCheckBoxAction()

        useWeaponPoisonCheckBox.onAction = useWeaponPoisonCheckBoxAction()

        weaponPoisonComboBox.items.setAll(*WeaponPoison.values())
        weaponPoisonComboBox.selectionModel.selectFirst()

        startButton.onAction = startButtonActionEvent()
    }

    private fun setup() {
        bot.ascensionSettings.apply {
            food = foodComboBox.selectionModel.selectedItem
            prayerPotion = prayerPotionComboBox.selectionModel.selectedItem
            isUsingFood = useFoodCheckBox.isSelected
            isUsingInvention = useInventionCheckBox.isSelected
            preset = presetComboBox.selectionModel.selectedItem
            isUsingPrayer = usePrayerPotionCheckBox.isSelected
            isUsingMagicNotepaper = magicNotepaperCheckBox.isSelected
            isUsingPotion = usePotionCheckBox.isSelected
            potion = potionComboBox.selectionModel.selectedItem
            weaponPoison = weaponPoisonComboBox.value
            isUsingWeaponPoison = useWeaponPoisonCheckBox.isSelected
        }
    }

    private fun usePrayerCheckBoxAction() = EventHandler<ActionEvent> {
        prayerPotionComboBox.isDisable = !usePrayerPotionCheckBox.isSelected
    }

    private fun usePotionCheckBoxAction() = EventHandler<ActionEvent> {
        potionComboBox.isDisable = !usePotionCheckBox.isSelected
    }

    private fun useFoodCheckBoxAction() = EventHandler<ActionEvent> {
        foodComboBox.isDisable = !useFoodCheckBox.isSelected
    }

    private fun useWeaponPoisonCheckBoxAction() = EventHandler<ActionEvent> {
        weaponPoisonComboBox.isDisable = !useWeaponPoisonCheckBox.isSelected
    }

    private fun startButtonActionEvent() = EventHandler<ActionEvent> {
        startButton.text = "Update"
        setup()
        bot.tasks.clear()
        bot.add(*bot.tasks())
    }
}
