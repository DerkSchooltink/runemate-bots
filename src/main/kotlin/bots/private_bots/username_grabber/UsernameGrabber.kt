package bots.private_bots.username_grabber

import bots.private_bots.username_grabber.ui.UsernameGrabberController
import quantum.api.core.ExtendedTaskBot
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
import com.runemate.game.api.hybrid.local.WorldOverview
import javafx.application.Platform
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.text.SimpleDateFormat
import java.util.*

class UsernameGrabber : ExtendedTaskBot() {

    val listenerManager = ListenerManager()
    val breakHandler = BreakManager()
    val controller = UsernameGrabberController(this)
    var isP2p: Boolean = false
    var isHopWorlds: Boolean = false
    val handledWorlds = HashSet<Int>()

    init {
        try {
            val file = File("usernames.txt")
            if (!file.exists() && file.createNewFile()) {
                val date = Date()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                textFileUpdater("----------- " + dateFormat.format(date) + " -----------")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onStart(vararg args: String) {
        setLoopDelay(1200, 1800)
        embeddableUI = UIExtender(this, controller, "com/bots/private_bots/username_grabber/ui/usernamegrabber.fxml", listenerManager, breakHandler)
    }

    fun textFileUpdater(username: String) {
        try {
            Files.write(Paths.get("usernames.txt"), (username + System.lineSeparator()).toByteArray(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
        } catch (e: IOException) {
            e.printStackTrace()
            textFileUpdater(username)
        }

    }

    fun addNameToListView(name: String) = Platform.runLater {
        if (controller.usernameListView.items?.contains(name) == false) {
            controller.usernameListView.items.add(name)
        }
    }

    fun addToHandledWorlds(world: WorldOverview) {
        handledWorlds.add(world.id)
    }

    fun setMember(member: Boolean) {
        isP2p = member
    }
}
