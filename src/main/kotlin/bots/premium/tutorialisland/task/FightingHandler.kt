package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution

class FightingHandler : ExtendedTask<TutorialIsland>() {

    private val combatInstructorCoordinate = Coordinate(3104, 9509, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 10 || it == 11 || it == 12 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            370 -> bot.talkToInstructor("Combat Instructor", combatInstructorCoordinate)
            390 -> ControlPanelTab.EQUIPMENT.open()
            400 -> openStatisticsTab()
            405 -> wieldItem("Bronze dagger")
            410 -> talkToInstructor()
            420 -> wieldSwordAndShield()
            430 -> ControlPanelTab.COMBAT_OPTIONS.open()
            440 -> enterRatCage()
            450, 460 -> if (!inRatCage()) enterRatCage() else if (!isAttackingRat()) attackRat()
            470 -> if (inRatCage()) leaveRatCage() else bot.talkToInstructor("Combat Instructor", combatInstructorCoordinate)
            480, 490 -> if (Equipment.contains("Shortbow") && Equipment.contains("Bronze arrow")) attackRat() else wearBowAndArrows()
            500 -> walkToLadder()
        }
    }

    private fun talkToInstructor() {
        val window = Interfaces.newQuery().actions("Close").results().first()
        if (window?.isVisible == true) window.click() else bot.talkToInstructor("Combat Instructor", combatInstructorCoordinate)
    }

    private fun wearBowAndArrows() {
        if (Equipment.newQuery().names("Shortbow").results().isEmpty()) {
            wieldItem("Shortbow")
        }
        if (Equipment.newQuery().names("Bronze arrow").results().isEmpty()) {
            wieldItem("Bronze arrow")
        }
    }

    private fun isAttackingRat() = Npcs.newQuery().names("Giant rat").targeting(Players.getLocal()).results().isNotEmpty()

    private fun wieldSwordAndShield() {
        if (Equipment.newQuery().names("Bronze sword").results().isEmpty()) {
            wieldItem("Bronze sword")
        }
        if (Equipment.newQuery().names("Wooden shield").results().isEmpty()) {
            wieldItem("Wooden shield")
        }
    }

    private fun walkToLadder() {
        val ladder = GameObjects.newQuery().names("Ladder").actions("Climb-up").results().nearest()
        if (ladder?.isVisible == false) {
            BresenhamPath.buildTo(ladder)?.step()
        } else ladder?.turnAndInteract("Climb-up")
    }

    private fun inRatCage() = Npcs.newQuery().names("Combat Instructor").reachable().results().isEmpty()

    private fun enterRatCage(): Boolean {
        val gate = GameObjects.newQuery().names("Gate").actions("Open").results().nearest()
        return if (gate == null || !gate.isVisible) {
            PathBuilder.buildTo(Coordinate(3113, 9521, 0))?.step() == true
        } else gate.turnAndInteract("Open") && Execution.delayUntil((::inRatCage), 1200, 1800)
    }

    private fun leaveRatCage() = GameObjects.newQuery().names("Gate").actions("Open").results().nearest()?.turnAndInteract("Open") == true
            && Execution.delayUntil({ !inRatCage() }, 1800, 2200)

    private fun attackRat() {
        if (Players.getLocal()?.target == null) {
            val giantRat = Npcs.newQuery().names("Giant rat").filter { it.target == null || it.target == Players.getLocal() }.results().nearest()
            if (giantRat != null && giantRat.turnAndInteract("Attack")) {
                Execution.delayUntil({ !giantRat.isValid }, 1200, 1800)
            }
        }
    }

    private fun closeEquipmentDetailWindow() = Interfaces.newQuery().actions("Close").results().first()?.click() == true

    private fun wieldItem(name: String) {
        Inventory.newQuery().names(name).results().first()?.let {
            if (it.click()) {
                Execution.delayUntil({ !it.isValid }, 1200, 1800)
            }
        }
    }

    private fun openStatisticsTab() {
        Interfaces.newQuery().actions("View equipment stats").results().first()?.interact("View equipment stats")
    }
}
