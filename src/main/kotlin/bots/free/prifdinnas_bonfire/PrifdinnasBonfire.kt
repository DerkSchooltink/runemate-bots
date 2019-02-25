package bots.free.prifdinnas_bonfire

import bots.free.prifdinnas_bonfire.task.ClaimSpirit
import bots.free.prifdinnas_bonfire.task.LightLogs
import bots.free.prifdinnas_bonfire.task.OpenBank
import bots.free.prifdinnas_bonfire.task.WithdrawPreset
import bots.free.prifdinnas_bonfire.ui.PrifdinnasBonfireUI
import com.runemate.game.api.hybrid.local.hud.interfaces.Chatbox
import com.runemate.game.api.script.framework.listeners.ChatboxListener
import com.runemate.game.api.script.framework.listeners.events.MessageEvent
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot

class PrifdinnasBonfire : ExtendedTaskBot(), ChatboxListener {

    var preset: Int = 0
    var amountToWaitForLooting = 6

    override fun onStart(vararg args: String) {
        embeddableUI = UIExtender(
            this,
            PrifdinnasBonfireUI(this),
            "com/bots/free/prifdinnas_bonfire/ui/prifdinnas_bonfire_ui.fxml"
        )
        setLoopDelay(1000, 1700)
    }

    fun setTasks() {
        add(ClaimSpirit(), LightLogs(), OpenBank(), WithdrawPreset())
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("Item could not be found:")) {
            stop("Bank is empty!")
        }
    }
}