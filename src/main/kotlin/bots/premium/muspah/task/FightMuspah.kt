package bots.premium.muspah.task

import bots.premium.muspah.Muspah
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.region.GroundItems
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.script.Execution

class FightMuspah : ExtendedTask<Muspah>() {

    override fun validate() = !Inventory.isFull()
            && !npcTargetingPlayer()
            && playerIsNotMoving()
            && monstersNearby()
            && GroundItems.newQuery().names(*bot.settings.allLoot).results().isEmpty()

    override fun execute() {
        logger.info("Attacking muspah...")
        Npcs.newQuery().names(*bot.settings.muspahNames).filter {
            it.target == null || it.target == Players.getLocal()
        }.results().nearest()?.let {
            if (Distance.to(it) > 10 || it.visibility < 50) {
                PathBuilder.buildTo(it)?.step()
            } else {
                if (it.turnAndInteract("Attack")) {
                    logger.info("Attacking muspah...")
                    Execution.delayUntil({ !it.isValid }, { Players.getLocal()?.animationId != -1 }, 600, 1200)
                } else {
                    it.turnAndInteract("Attack")
                    logger.info("Attacking muspah...")
                }
            }
        }
    }

    private fun playerIsNotMoving() = Players.getLocal()?.run { isMoving } == false

    private fun npcTargetingPlayer() = Players.getLocal()?.target != null || !Npcs.newQuery().names(*bot.settings.muspahNames).filter { monster ->
        monster.healthGauge?.percent != 0
    }.targeting(Players.getLocal()).results().isEmpty()

    private fun monstersNearby() = !Npcs.newQuery().names(*bot.settings.muspahNames).results().isEmpty()
}