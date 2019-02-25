package bots.premium.citadel

import bots.premium.citadel.task.LootAnagogicOrt
import bots.premium.citadel.ui.CitadelUI
import com.runemate.game.api.client.ClientUI
import com.runemate.game.api.hybrid.local.hud.interfaces.Chatbox
import com.runemate.game.api.script.framework.listeners.ChatboxListener
import com.runemate.game.api.script.framework.listeners.events.MessageEvent
import com.runemate.game.api.script.framework.task.Task
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class Citadel : ExtendedTaskBot(), ChatboxListener {

    init {
        embeddableUI = UIExtender(this, CitadelUI(this), "com/bots/premium/citadel/ui/citadel_ui.fxml")
        eventDispatcher.addListener(this)
    }

    override fun onStart(vararg args: String) {
        setLoopDelay(800, 1600)
    }

    fun setSettings(task: Task?, collectOrt: Boolean) {
        tasks.clear()
        if (collectOrt) {
            add(LootAnagogicOrt())
        }
        if (task != null) {
            add(task)
        } else {
            ClientUI.sendTrayNotification("No task selected, please change UI settings and select a task!")
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("You have reached your resource cap, you will not be able to skill until the next build tick.")) {
            logger.warn("Limit reached, stopping bot...")
            stop("Limit reached, stopping bot...")
        }
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("You can't summon any more minions until your next build tick.")) {
            ClientUI.sendTrayNotification("You have reached the maximum amount of minions! The bot has been pauzed.")
            pause()
        }
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("This skill plot is currently locked.")) {
            ClientUI.sendTrayNotification("Skill plot is locked, the bot is now paused!")
            pause()
        }
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("Your clan has run out of resources needed to work with the furnace.")) {
            ClientUI.sendTrayNotification("Resources depleted, the bot is now paused!")
            pause()
        }
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("Your citadel does not have enough charcoal to make")) {
            ClientUI.sendTrayNotification("Resources depleted, the bot is now paused!")
            pause()
        }
    }
}