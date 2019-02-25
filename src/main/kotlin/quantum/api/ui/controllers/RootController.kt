package quantum.api.ui.controllers

import quantum.api.framework.extensions.defaultStyle
import quantum.api.framework.extensions.highlight
import quantum.api.ui.animations.Animations
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
import quantum.api.ui.managers.ThreadManager
import com.runemate.game.api.hybrid.util.Resources
import com.runemate.game.api.hybrid.util.StopWatch
import com.runemate.game.api.script.framework.AbstractBot
import com.runemate.game.api.script.framework.core.LoopingThread
import com.runemate.game.api.script.framework.logger.BotLogger
import com.runemate.game.api.script.framework.logger.BotLoggerListener
import com.runemate.game.api.script.framework.logger.LoggedMessageEvent
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.*

class RootController(private var bot: AbstractBot, private var settingsPath: String?, private var controller: kotlin.Any?, private var listenerManager: ListenerManager?, private var breakManager: BreakManager?) : StackPane(), Initializable, BotLoggerListener {

    @FXML
    private lateinit var parentPane: StackPane
    @FXML
    private lateinit var mainPane: StackPane
    @FXML
    private lateinit var menuImageView: ImageView
    @FXML
    private lateinit var menuImage: Image
    @FXML
    private lateinit var sidebarPane: StackPane
    @FXML
    private lateinit var runtimeLabel: Label
    @FXML
    private lateinit var debugLabel: Label
    @FXML
    private lateinit var statusLabel: Label
    @FXML
    private lateinit var settingsMenu: StackPane
    @FXML
    private lateinit var skillsMenu: StackPane
    @FXML
    private lateinit var aliasMenu: StackPane
    @FXML
    private lateinit var occultMenu: StackPane
    @FXML
    private lateinit var itemsMenu: StackPane
    @FXML
    private lateinit var breaksMenu: StackPane
    @FXML
    private lateinit var debugMenu: StackPane
    @FXML
    private lateinit var mainScrollPane: ScrollPane

    private var runtimeProperty = SimpleStringProperty("00:00:00")
    private var debugLevelProperty = SimpleStringProperty("Information")
    private var statusProperty = SimpleStringProperty("Initialized UI")
    private var timer = StopWatch()
    private var activeNode: Node? = null
    private var threadManager = ThreadManager()

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        // Making sure sidebar isn't visible on load
        sidebarPane.isVisible = false
        Animations.fadeAndTranslateX(sidebarPane, 0.0, 0.0, -150.0, 100.0, 0.0).play()

        // Starting timer
        timer.start()

        // Setting up the LoopingThread for the runtime
        val runtimeThread = LoopingThread(this::updateRunTime, 1000)
        runtimeThread.start()

        // Load images and apply on mainPane frame
        bot.platform.invokeAndWait {
            menuImage = Image(Resources.getAsStream("com/quantum/api/ui/images/menu.png"))
            Platform.runLater {
                menuImageView.image = menuImage
            }
        }

        // Loading settings tab
        loadSettings()
        // Loading skill tab
        loadElement(skillsMenu, "com/quantum/api/ui/fxml/Skills.fxml", SkillController(bot, threadManager))
        // Loading items tab
        loadElement(itemsMenu, "com/quantum/api/ui/fxml/Items.fxml", ItemsController(bot, threadManager))
        // Loading occult tab
        loadElement(occultMenu, "com/quantum/api/ui/fxml/Occult.fxml", OccultController())
        // Loading break tab

        // Loading debug tab

        // Loading developer tab

        // Loading alias switcher tab
        //loadElement(aliasMenu, "com/quantum/api/ui/fxml/Alias.fxml", AliasController(bot))
        // Bind labels
        bindLabels()
        // Adding effects for menuImageView / sidebarPane
        addMenuImageEffects()
        // Adding effects for all sidebarPane elements
        addSideBarMenuEffects()

        // Adding the listener to the bot event dispatcher
        bot.eventDispatcher.addListener(this)

        mainScrollPane.maxWidth = parentPane.width

        addListeners()
        addCSS()
    }

    private fun resetScrollValue() {
        mainScrollPane.vvalue = 0.0
    }

    private fun addCSS() {
        bot.platform.invokeLater {
            val quantum: File? = quantum.api.framework.utils.Resources.writeToFile("com/quantum/api/ui/revamped/css/quantum.css")
            if (quantum != null) {
                quantum.deleteOnExit()
                val uri = quantum.toURI().toString()
                bot.logger.debug("URI: $uri")
                Platform.runLater { stylesheets.add(uri) }
            }
        }
    }

    private fun addListeners() {
        parentPane.widthProperty().addListener { _, _, _ ->
            if (sidebarPane.isVisible) mainScrollPane.maxWidth = parentPane.width - (sidebarPane.prefWidth + 5)
            else mainScrollPane.maxWidth = parentPane.width
        }

        sidebarPane.translateXProperty().addListener { _, _, _ ->
            if (sidebarPane.opacity > 0.75) mainScrollPane.maxWidth = parentPane.width - (sidebarPane.translateX + (sidebarPane.prefWidth + 5))
            else mainScrollPane.maxWidth = parentPane.width - (sidebarPane.translateX + sidebarPane.prefWidth)
        }
    }

    private fun loadElement(enabler: StackPane, fxml: String, controller: Any) {
        bot.platform.invokeLater {
            val stream = Resources.getAsStream(fxml)
            Platform.runLater {
                val loader = FXMLLoader()
                loader.setRoot(controller)
                loader.setController(controller)
                try {
                    val node = loader.load<Node>(stream)
                    mainPane.children.add(node)
                    enabler.setOnMouseClicked { toggleElement(node) }
                    node.isVisible = false
                    bot.logger.debug("Main pane content: ${mainPane.children}")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun toggleElement(node: Node?) {
        if (node != null) {
            resetScrollValue()
            if (!node.isVisible) {
                toggleSidebar()
                if (activeNode != null && activeNode!!.isVisible) {
                    val animation = Animations.fadeOutAndScale(activeNode!!, 50.0, 0.0)
                    animation.setOnFinished {
                        activeNode!!.isVisible = false
                    }
                    animation.play()

                }
                // Fade in node
                node.isVisible = true
                val animation = Animations.fadeInAndScale(node, 150.0, 50.0)
                animation.setOnFinished {
                    activeNode = node
                }
                animation.play()
            } else if (activeNode == null) activeNode = node

        } else bot.logger.warn("[Root] Trying to toggle null Node")
    }

    private fun toggleSidebar() {
        if (sidebarPane.isVisible) {
            val anim = Animations.fadeAndTranslateX(sidebarPane, 0.0, 0.0, -150.0, 100.0, 0.0)
            anim.setOnFinished {
                sidebarPane.isVisible = false
            }
            anim.play()
        } else {
            val anim = Animations.fadeAndTranslateX(sidebarPane, 1.0, -150.0, 0.0, 100.0, 0.0)
            anim.setOnFinished {
                sidebarPane.isVisible = true
            }
            anim.play()
        }
    }

    private fun addSideBarMenuEffects() {
        settingsMenu.setOnMouseEntered { highlight(settingsMenu) }
        settingsMenu.setOnMouseExited { defaultStyle(settingsMenu) }

        skillsMenu.setOnMouseEntered { highlight(skillsMenu) }
        skillsMenu.setOnMouseExited { defaultStyle(skillsMenu) }

        occultMenu.setOnMouseEntered { highlight(occultMenu) }
        occultMenu.setOnMouseExited { defaultStyle(occultMenu) }

        itemsMenu.setOnMouseEntered { highlight(itemsMenu) }
        itemsMenu.setOnMouseExited { defaultStyle(itemsMenu) }

        aliasMenu.setOnMouseEntered { highlight(aliasMenu) }
        aliasMenu.setOnMouseExited { defaultStyle(aliasMenu) }

        breaksMenu.setOnMouseEntered { highlight(breaksMenu) }
        breaksMenu.setOnMouseExited { defaultStyle(breaksMenu) }

        debugMenu.setOnMouseEntered { highlight(debugMenu) }
        debugMenu.setOnMouseExited { defaultStyle(debugMenu) }
    }

    private fun addMenuImageEffects() {
        menuImageView.setOnMouseEntered {
            cursor = Cursor.HAND
            Animations.scale(menuImageView, 1.1, 125.0, 0.0).play()
        }
        menuImageView.setOnMouseExited {
            cursor = Cursor.DEFAULT
            Animations.scale(menuImageView, 1.0, 125.0, 0.0).play()
        }
        menuImageView.setOnMouseClicked {
            if (sidebarPane.isVisible) {
                val fadeOut = Animations.fadeAndTranslateX(sidebarPane, 0.0, 0.0, -150.0, 150.0, 0.0)
                fadeOut.setOnFinished {
                    sidebarPane.isVisible = false
                }
                fadeOut.play()
            } else {
                sidebarPane.isVisible = true
                val fadeOut = Animations.fadeAndTranslateX(sidebarPane, 0.95, -150.0, 0.0, 150.0, 0.0)
                fadeOut.play()
            }
        }
    }

    private fun updateRunTime() {
        Platform.runLater {
            runtimeProperty.set(timer.runtimeAsString)
        }
    }

    private fun bindLabels() {
        runtimeLabel.textProperty().bind(runtimeProperty)
        debugLabel.textProperty().bind(debugLevelProperty)
        statusLabel.textProperty().bind(statusProperty)
    }

    private fun loadSettings() {
        bot.platform.invokeLater {
            val stream: InputStream = Resources.getAsStream(settingsPath)
            Platform.runLater {
                val loader = FXMLLoader().apply {
                    setRoot(controller)
                    setController(controller)
                }
                try {
                    val node: Node = loader.load(stream)
                    mainPane.children.add(node)
                    settingsMenu.setOnMouseClicked {
                        toggleElement(node)
                    }
                    activeNode = node
                    bot.logger.debug("Main pane children: ${mainPane.children}")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun logged(logger: LoggedMessageEvent) {
        val message: BotLogger.Message? = logger.message
        val level: BotLogger.Level? = message?.level
        if (level != BotLogger.Level.FINE && level != BotLogger.Level.DEBUG && message != null && level != null) {
            Platform.runLater {
                statusProperty.set(message.message)
                debugLevelProperty.set(level.getName())
            }
        }
    }
}