package quantum.api.ui.controllers

import quantum.api.framework.extensions.defaultStyle
import quantum.api.framework.extensions.highlight
import quantum.api.ui.animations.Animations
import quantum.api.ui.calculations.Calculations
import quantum.api.ui.data.Skill
import quantum.api.ui.managers.ThreadManager
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.listeners.SkillListener
import com.runemate.game.api.script.framework.listeners.events.SkillEvent
import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import javafx.scene.layout.StackPane
import java.io.IOException
import java.net.URL
import java.util.*

class SkillController(var bot: AbstractBot, private var threadManager: ThreadManager) : StackPane(), Initializable, SkillListener {

    // FXML stuff
    @FXML
    private lateinit var levelsGainedLabel: Label
    @FXML
    private lateinit var xpGainedLabel: Label
    @FXML
    private lateinit var xpHourlyLabel: Label
    @FXML
    private lateinit var trackerHolderPane: FlowPane
    @FXML
    private lateinit var overviewMenuPane: StackPane
    @FXML
    private lateinit var trackerMenuPane: StackPane
    @FXML
    private lateinit var main: StackPane

    //private var threadManager = ThreadManager()
    private var timer = StopWatch()
    private var trackerNodeList: MutableList<Node> = mutableListOf()

    // Data class related
    private var skillDataClassList = mutableListOf<Skill>()
    private var skillTrackerControllerList = mutableListOf<SkillTrackerController>()

    // Properties
    private var totalLevelsGainedProperty = SimpleIntegerProperty(0)
    private var totalExperienceGainedProperty = SimpleIntegerProperty(0)
    private var hourlyExperienceGainedProperty = SimpleIntegerProperty(0)

    // Normal values
    private var totalLevelsGained = 0
    private var totalExperienceGained = 0
    private var hourlyExperienceGained = 0

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // Adding listener to bot event dispatcher
        bot.platform.invokeLater { bot.eventDispatcher.addListener(this) }

        // JavaFX
        addHoverEffects()

        // Binding Properties to Labels
        levelsGainedLabel.textProperty().bind(totalLevelsGainedProperty.asString("%,d"))
        xpGainedLabel.textProperty().bind(totalExperienceGainedProperty.asString("%,d"))
        xpHourlyLabel.textProperty().bind(hourlyExperienceGainedProperty.asString("%,d"))

        // Listeners
        main.visibleProperty().addListener { _, _, newValue -> if (newValue == true) show() else hide() }
        threadManager.add { setProperties() }
    }

    override fun onExperienceGained(event: SkillEvent?) {
        if (!timer.isRunning) timer.start()
        processEvent(event)
    }

    override fun onLevelUp(event: SkillEvent?) {
        totalLevelsGained++
    }

    private fun processEvent(event: SkillEvent?) {
        val change = event?.change ?: 0
        updateValues(change)
        val skill = event?.skill
        if (skill != null) {
            bot.platform.invokeLater {
                if (skillDataClassList.find { it.skill == skill } == null) {
                    val skillDataClass = Skill(skill.name, skill, skill.currentLevel, change, skill.experienceToNextLevel)
                    bot.logger.debug("Created Data class[$skillDataClass]")
                    skillDataClassList.add(skillDataClass)

                    // Adding setProperties() to the list so they can all be updated at the same time
                    val properties = skillDataClass.Properties(timer)
                    threadManager.add { properties.setProperties() }
                    loadTracker(skillDataClass, properties)
                } else {
                    val skillDataClass = skillDataClassList.find { it.skill == skill }
                    if (skillDataClass != null) {
                        skillDataClass.experienceGained += change
                        skillDataClass.experienceToNextLevel = skill.experienceToNextLevel
                        skillDataClass.level = skill.currentLevel
                    }
                }
            }
        }
    }

    private fun loadTracker(skillDataClass: Skill, properties: Skill.Properties) {
        bot.platform.invokeLater {
            val stream = Resources.getAsStream("com/quantum/api/ui/fxml/SkillTracker.fxml")
            Platform.runLater {
                val loader = FXMLLoader()
                val controller = SkillTrackerController(bot, skillDataClass, timer, threadManager, properties)
                loader.setRoot(controller)
                loader.setController(controller)
                try {
                    val node = loader.load<Node>(stream)
                    trackerHolderPane.children.add(node)
                    trackerNodeList.add(node)
                    skillTrackerControllerList.add(controller)
                    skillTrackerControllerList.forEach { it.currentIndex = 1 }
                    Animations.fadeAndTranslateX(node, 1.0, 175.0, 0.0, 200.0, 50.0).play()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun updateValues(change: Int) {
        totalExperienceGained += change
        hourlyExperienceGained = Calculations.hourlyRate(totalExperienceGained, timer)
        setProperties()
    }

    private fun setProperties() {
        Platform.runLater {
            totalLevelsGainedProperty.set(totalLevelsGained)
            totalExperienceGainedProperty.set(totalExperienceGained)
            hourlyExperienceGainedProperty.set(Calculations.hourlyRate(totalExperienceGained, timer))
        }
    }

    private fun addHoverEffects() {
        overviewMenuPane.setOnMouseEntered { highlight(overviewMenuPane) }
        overviewMenuPane.setOnMouseExited { defaultStyle(overviewMenuPane) }

        trackerMenuPane.setOnMouseEntered { highlight(trackerMenuPane) }
        trackerMenuPane.setOnMouseExited { defaultStyle(trackerMenuPane) }
    }

    private fun show() {
        var delay = 225.0
        trackerNodeList.forEach {
            Animations.fadeAndTranslateX(it, 1.0, 175.0, 0.0, 175.0, delay).play()
            delay += 125.0
        }
    }

    private fun hide() {
        trackerNodeList.forEach { it.opacity = 0.0 }
    }
}