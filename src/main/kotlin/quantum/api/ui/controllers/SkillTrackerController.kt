package quantum.api.ui.controllers

import quantum.api.framework.utils.Resources
import quantum.api.ui.animations.Animations
import quantum.api.ui.data.Skill
import quantum.api.ui.managers.ThreadManager
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.script.framework.AbstractBot
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.FlowPane
import javafx.scene.layout.StackPane
import java.net.URL
import java.util.*

class SkillTrackerController(var bot: AbstractBot, private var skillDataClass: Skill, var timer: StopWatch, private var threadManager: ThreadManager, private var p: Skill.Properties) : StackPane(), Initializable {

    // FXML stuff
    @FXML
    private lateinit var skillNameLabel: Label
    @FXML
    private lateinit var skillImageView: ImageView

    @FXML
    private lateinit var xpGainedLabel: Label
    @FXML
    private lateinit var xpHourlyLabel: Label
    @FXML
    private lateinit var currentLevelLabel: Label
    @FXML
    private lateinit var toNextLevelLabel: Label

    @FXML
    private lateinit var xpGainedPane: StackPane
    @FXML
    private lateinit var xpHourlyPane: StackPane
    @FXML
    private lateinit var currentLevelPane: StackPane
    @FXML
    private lateinit var toNextLevelPane: StackPane

    private var displayPanes: MutableList<StackPane> = mutableListOf()
    private var activePane: StackPane? = null
    var currentIndex = 1

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        FlowPane.setMargin(this, Insets(0.0, 0.0, 5.0, 5.0))
        bot.platform.invokeLater {
            try {
                val image = Resources.imageFromURL("https://www.runemate.com/botstore/images/skills/clear/${skillDataClass.name.toLowerCase()}.png")
                Platform.runLater {
                    skillImageView.image = image
                }
            } catch (e: Exception) {
                e.printStackTrace()
                bot.logger.warn("Error loading image for ${skillDataClass.name}")
            }
        }

        // Force setting name to label
        skillNameLabel.text = skillDataClass.name.toLowerCase().capitalize()

        // Setting up properties
        xpGainedLabel.textProperty().bind(p.totalExperienceGainedProperty.asString("%,d"))
        xpHourlyLabel.textProperty().bind(p.hourlyExperienceGainedProperty.asString("%,d"))
        currentLevelLabel.textProperty().bind(p.currentLevelProperty.asString("%,d"))
        toNextLevelLabel.textProperty().bind(p.timeToNextLevelProperty)
        p.setProperties()

        // Setting defaults for Animations / JavaFX
        displayPanes.add(xpGainedPane)
        displayPanes.add(xpHourlyPane)
        displayPanes.add(currentLevelPane)
        displayPanes.add(toNextLevelPane)
        activePane = xpGainedPane

        threadManager.add { switchPanes(currentIndex) }
    }

    private fun switchPanes(_index: Int) {
        var index = _index
        if (index >= displayPanes.count()) {
            index = 0
            currentIndex = 0
        }

        activePane?.let {
            Platform.runLater {
                val anim = Animations.fadeAndTranslateX(it, 0.0, 0.0, -100.0, 80.0, 0.0)
                anim.setOnFinished { activePane = null }
                anim.play()
            }

        }
        val paneToDisplay = displayPanes[index]
        Platform.runLater {
            val anim = Animations.fadeAndTranslateX(paneToDisplay, 1.0, 100.0, 0.0, 80.0, 55.0)
            anim.setOnFinished { activePane = paneToDisplay }
            anim.play()
        }
        currentIndex++
    }
}