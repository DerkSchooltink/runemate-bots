package quantum.api.ui.data

import quantum.api.ui.calculations.Calculations
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.util.StopWatch
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

data class Item(
        val timer: StopWatch,
        val item: SpriteItem,
        val name: String,
        val price: Int,
        val amount: Int,

        val nameProperty: SimpleStringProperty = SimpleStringProperty("Unknown"),
        val totalAmountProperty: SimpleIntegerProperty = SimpleIntegerProperty(0),
        val hourlyAmountProperty: SimpleIntegerProperty = SimpleIntegerProperty(0),
        val totalProfitProperty: SimpleIntegerProperty = SimpleIntegerProperty(0),
        val hourlyProfitProperty: SimpleIntegerProperty = SimpleIntegerProperty(0)
) {

    fun update(change: Int) = Platform.runLater {
        totalAmountProperty.set(totalAmountProperty.get() + change)
        hourlyAmountProperty.set(Calculations.hourlyRate(totalAmountProperty.get(), timer))

        totalProfitProperty.set(totalProfitProperty.get() + (change * price))
        hourlyProfitProperty.set(Calculations.hourlyRate(totalProfitProperty.get(), timer))
    }

    fun updateHourly() = Platform.runLater {
        hourlyAmountProperty.set(Calculations.hourlyRate(totalAmountProperty.get(), timer))
        hourlyProfitProperty.set(Calculations.hourlyRate(totalProfitProperty.get(), timer))
    }

    fun initialize() = Platform.runLater {
        nameProperty.set(name)
        totalAmountProperty.set(amount)
        hourlyAmountProperty.set(Calculations.hourlyRate(amount, timer))
        totalProfitProperty.set(amount * price)
        hourlyProfitProperty.set(Calculations.hourlyRate(totalProfitProperty.get(), timer))
    }
}