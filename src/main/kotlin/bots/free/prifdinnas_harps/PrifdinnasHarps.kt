package bots.free.prifdinnas_harps

import bots.free.prifdinnas_harps.ui.PrifdinnasHarpsUI
import quantum.api.ui.extenders.UIExtender
import quantum.api.ui.managers.BreakManager
import quantum.api.ui.managers.ListenerManager
import com.runemate.game.api.hybrid.GameEvents
import com.runemate.game.api.hybrid.local.hud.interfaces.Chatbox
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import com.runemate.game.api.script.framework.LoopingBot
import com.runemate.game.api.script.framework.listeners.ChatboxListener
import com.runemate.game.api.script.framework.listeners.events.MessageEvent

class PrifdinnasHarps : LoopingBot(), ChatboxListener {

    private val listenerManager = ListenerManager()
    private val breakHandler = BreakManager()
    private var messageReceived = false

    var isSleeper: Boolean? = false
    var isLootingOrt: Boolean? = false
    var percentage: Int? = null
    var coordinate = Coordinate(1, 1, 1)

    override fun onStart(vararg args: String) {
        embeddableUI = UIExtender(this, PrifdinnasHarpsUI(this), "com/bots/free/prifdinnas_harps/ui/PrifdinnasHarpsUI.fxml", listenerManager, breakHandler)
        //disabled item handler because of the golden rocks that people often hunt for
        GameEvents.Universal.UNEXPECTED_ITEM_HANDLER.disable()
        setLoopDelay(600, 1500)
        eventDispatcher.addListener(this)
    }

    override fun onLoop() {
        if (messageReceived || Players.getLocal()?.animationId == -1) {
            GameObjects.newQuery().on(coordinate).names("Harp").actions("Tune", "Play").results().first()?.let {
                if (isSleeper == true) {
                    logger.info("Sleeping...")
                    Execution.delay(4500, 15500)
                }
                if (it.click()) {
                    logger.info("Tuning harp...")
                    messageReceived = false
                }
            }
        }

        if (isLootingOrt == true) {
            GroundItems.newQuery().names("Anagogic ort").results().nearest()?.let {
                if (it.interact("Take")) {
                    logger.info("Looting anagogic ort...")
                    Execution.delayUntil({ !it.isValid }, 300, 1000)
                }
            }
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.type == Chatbox.Message.Type.SERVER && messageEvent.message.contains("Your harp is $percentage% out of tune.")) {
            messageReceived = true
        }
    }
}
