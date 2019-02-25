package quantum.api.ui.extenders

import quantum.api.ui.controllers.RootController
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
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
    val controller: K,
    val fxmlPath: String,
    val listenerManager: ListenerManager,
    val breakHandler: BreakManager
) : EmbeddableUI {

    private var botInterfaceProperty: ObjectProperty<Node>? = null

    override fun botInterfaceProperty(): ObjectProperty<out Node>? {
        if (botInterfaceProperty == null) {
            val loader = FXMLLoader()
            val controller = RootController(bot, fxmlPath, controller, listenerManager, breakHandler)

            loader.setRoot(controller)
            loader.setController(controller)

            try {
                val node: Node = loader.load(Resources.getAsStream("com/quantum/api/ui/fxml/Root.fxml"))
                botInterfaceProperty = SimpleObjectProperty<Node>(node)
                fitToHeightProperty().set(true)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return botInterfaceProperty
    }
}