package quantum.api.ui.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class OccultController : StackPane(), Initializable {

    @FXML
    private lateinit var mainLabel: Label
    @FXML
    private lateinit var progressIndicator: ProgressIndicator

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        progressIndicator.progress = ProgressIndicator.INDETERMINATE_PROGRESS
    }
}