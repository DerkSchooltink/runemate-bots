package bots.premium.slayer.tasks

import bots.premium.slayer.Slayer
import quantum.api.core.ExtendedTask
import quantum.api.framework.extensions.names
import quantum.api.framework.playersense.QuantumPlayerSense
import com.runemate.game.api.hybrid.local.hud.interfaces.Health
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class EmergencyTeleport : ExtendedTask<Slayer>() {

    private val teleportItemPattern = Pattern.compile("(.* teleport)")
    private var teleportItem: SpriteItem? = null

    override fun validate(): Boolean {
        teleportItem = Inventory.newQuery().names(teleportItemPattern).results().first()
        return teleportItem != null
                && !Npcs.newQuery().names(bot.slayerSettings.task?.names).targeting(Players.getLocal()).results().isEmpty()
                && Inventory.newQuery().actions("Eat").results().isEmpty()
                && Health.getCurrentPercent() < QuantumPlayerSense.EMERGENCY_HEALTH_AMOUNT.asInteger()
    }

    override fun execute() {
        logger.info("Teleporting away...")
        teleportItem?.run {
            if (click()) {
                Execution.delay(1500, 2500)
            }
        }
    }
}
