package quantum.api.ui.controllers

import quantum.api.ui.animations.Animations
import quantum.api.ui.calculations.Calculations
import quantum.api.ui.data.Item
import quantum.api.ui.managers.ThreadManager
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.net.GrandExchange
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.listeners.InventoryListener
import com.runemate.game.api.script.framework.listeners.MoneyPouchListener
import com.runemate.game.api.script.framework.listeners.events.ItemEvent
import com.runemate.game.api.script.framework.listeners.events.MoneyPouchEvent
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import java.io.IOException
import java.net.URL
import java.util.*

class ItemsController(val bot: AbstractBot, private val threadManager: ThreadManager) : StackPane(), Initializable, InventoryListener, MoneyPouchListener {

    @FXML
    private lateinit var main: StackPane

    @FXML
    private lateinit var trackerHolderPane: VBox

    @FXML
    private lateinit var itemsLootedLabel: Label

    @FXML
    private lateinit var hourlyAmountLabel: Label

    @FXML
    private lateinit var profitGainedLabel: Label

    @FXML
    private lateinit var hourlyProfitLabel: Label

    private var timer: StopWatch = StopWatch()
    private var itemList: MutableList<String> = mutableListOf()
    private var itemDataList: MutableList<Item> = mutableListOf()

    private var itemsGainedProperty = SimpleIntegerProperty(0)
    private var hourlyAmountProperty = SimpleIntegerProperty(0)
    private var profitGainedProperty = SimpleIntegerProperty(0)
    private var hourlyProfitProperty = SimpleIntegerProperty(0)

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        timer.start()
        bot.platform.invokeLater {
            bot.eventDispatcher.addListener(this)
        }

        itemsLootedLabel.textProperty().bind(itemsGainedProperty.asString("%,d"))
        hourlyAmountLabel.textProperty().bind(hourlyAmountProperty.asString("%,d"))
        profitGainedLabel.textProperty().bind(profitGainedProperty.asString("%,d"))
        hourlyProfitLabel.textProperty().bind(hourlyProfitProperty.asString("%,d"))

        threadManager.add {
            update()
        }
    }

    override fun onContentsChanged(p0: MoneyPouchEvent?) = Unit
    override fun onItemAdded(event: ItemEvent?) =processEvent(event)
    override fun onItemRemoved(event: ItemEvent?) = processEvent(event)

    private fun update() {
        itemDataList.forEach {
            it.updateHourly()
        }
        updateHourlyProperties()
    }

    private fun addTracker(item: Item) {
        bot.logger.debug("Adding ItemTracker to $trackerHolderPane")
        bot.platform.invokeLater {
            val stream = Resources.getAsStream("com/quantum/api/ui/fxml/ItemTracker.fxml")
            Platform.runLater {
                val loader = FXMLLoader()
                val controller = ItemTrackerController(bot, item)
                loader.setRoot(controller)
                loader.setController(controller)
                try {
                    val node = loader.load<Node>(stream)
                    trackerHolderPane.children.add(node)
                    Animations.fadeInAndScale(node, 200.0, 50.0).play()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun processEvent(event: ItemEvent?) {
        val item: SpriteItem? = event?.item
        val def = item?.definition
        if (def != null && def.isTradeable) {

            val name = def.name
            val amount = if (event.type == ItemEvent.Type.ADDITION) event.quantityChange else 0.minus(event.quantityChange)

            if (itemList.contains(name)) {
                itemDataList.find { it.name == name }?.update(event.quantityChange)
                updateProperties(amount, GrandExchange.lookup(def.id)?.price ?: 0)
            } else {
                bot.logger.debug("[ItemEvent] Creating new DataClass -> $item")

                val price = GrandExchange.lookup(def.id)?.price ?: 0
                val itemDataClass = Item(timer, item, name, price, amount)

                itemDataList.add(itemDataClass)
                itemList.add(name)

                itemDataClass.initialize()
                addTracker(itemDataClass)
                updateProperties(amount, price)
            }
        }
    }

    private fun updateProperties(change: Int, price: Int) = Platform.runLater {
        itemsGainedProperty.set(itemsGainedProperty.get() + change)
        hourlyAmountProperty.set(Calculations.hourlyRate(itemsGainedProperty.get(), timer))
        profitGainedProperty.set(profitGainedProperty.get() + (change * price))
        hourlyProfitProperty.set(Calculations.hourlyRate(profitGainedProperty.get(), timer))
    }

    private fun updateHourlyProperties() = Platform.runLater {
        hourlyAmountProperty.set(Calculations.hourlyRate(itemsGainedProperty.get(), timer))
        hourlyProfitProperty.set(Calculations.hourlyRate(profitGainedProperty.get(), timer))
    }
}