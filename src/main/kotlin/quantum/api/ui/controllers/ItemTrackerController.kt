package quantum.api.ui.controllers

import quantum.api.ui.data.Item
import com.runemate.game.api.script.framework.AbstractBot
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class ItemTrackerController(var bot: AbstractBot, var item: Item) : StackPane(), Initializable {

    @FXML
    private lateinit var itemNameLabel: Label

    @FXML
    private lateinit var itemAmountLabel: Label

    @FXML
    private lateinit var itemHourlyAmountLabel: Label

    @FXML
    private lateinit var itemProfitLabel: Label

    @FXML
    private lateinit var itemHourlyProfitLabel: Label

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        itemNameLabel.textProperty().bind(item.nameProperty)
        itemAmountLabel.textProperty().bind(item.totalAmountProperty.asString("%,3d"))
        itemHourlyAmountLabel.textProperty().bind(item.hourlyAmountProperty.asString("%,3d"))
        itemProfitLabel.textProperty().bind(item.totalProfitProperty.asString("%,3d"))
        itemHourlyProfitLabel.textProperty().bind(item.hourlyProfitProperty.asString("%,3d"))
    }
}