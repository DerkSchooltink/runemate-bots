package bots.premium.muspah.ui

import bots.premium.muspah.task.*
import bots.premium.muspah.Muspah
import quantum.api.game.data.Overload
import quantum.api.game.data.PrayerPotion
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class MuspahController(val bot: Muspah) : StackPane(), Initializable {

    @FXML
    private lateinit var prayerPotionComboBox: ComboBox<PrayerPotion>

    @FXML
    private lateinit var overloadComboBox: ComboBox<Overload>

    @FXML
    private lateinit var startButton: Button

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        prayerPotionComboBox.items.setAll(*PrayerPotion.values())
        overloadComboBox.items.setAll(*Overload.values())

        prayerPotionComboBox.selectionModel.selectFirst()
        overloadComboBox.selectionModel.selectFirst()

        startButton.setOnAction {
            bot.tasks.clear()
            bot.add(ActivatePrayer(), DeactivatePrayer(), DrinkOverload(), DrinkPrayerPotion(), FightMuspah(), NoteLoot(), Loot())
            bot.settings.overload = overloadComboBox.value
            bot.settings.prayerPotion = prayerPotionComboBox.value
        }
    }
}