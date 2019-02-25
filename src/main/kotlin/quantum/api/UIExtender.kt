package quantum.api

import com.runemate.game.api.client.embeddable.EmbeddableUI
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.script.framework.AbstractBot
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import java.io.IOException

class UIExtender<T : AbstractBot, K : Initializable>(
    val bot: T,
    private val controller: K,
    private val fxmlPath: String
) : EmbeddableUI {

    var botInterfaceProperty: ObjectProperty<Node>? = null

    override fun botInterfaceProperty(): ObjectProperty<out Node>? {
        if (botInterfaceProperty == null) {
            val loader = FXMLLoader()

            loader.setRoot(controller)
            loader.setController(controller)

            try {
                val node: Node = loader.load(Resources.getAsStream(fxmlPath))
                botInterfaceProperty = SimpleObjectProperty<Node>(node)
                fitToHeightProperty().set(true)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return botInterfaceProperty
    }
}