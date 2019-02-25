package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import quantum.api.core.ExtendedTask
import quantum.api.game.Interaction
import quantum.api.game.turnAndInteract
import quantum.api.game.useOn
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Npcs
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution
import java.util.regex.Pattern

class SurvivalHandler : ExtendedTask<TutorialIsland>() {

    private val survivalCoordinate = Coordinate(3103, 3097, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 2 || it == 3 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            20 -> bot.talkToInstructor("Survival Expert", survivalCoordinate)
            30 -> ControlPanelTab.INVENTORY.open()
            40 -> chopTree()
            50 -> lightFire()
            60 -> ControlPanelTab.SKILLS.open()
            70 -> bot.talkToInstructor("Survival Expert", survivalCoordinate)
            80, 90, 100, 110 -> cookShrimp()
            120 -> openGate()
        }
    }

    private fun cookShrimp() {
        ControlPanelTab.INVENTORY.open()
        if (Inventory.getQuantity(Pattern.compile(".*shrimp.*")) < 2) {
            fish()
        } else if (GameObjects.newQuery().names("Fire").results().isEmpty()) {
            if (!Inventory.contains("Logs")) chopTree() else lightFire()
        } else cook()
    }

    private fun openGate(): Boolean {
        val gate = GameObjects.newQuery().names("Gate").actions("Open").results().nearest()
        return if (Distance.to(gate) < 3) {
            Camera.concurrentlyTurnTo(gate)
            gate.turnAndInteract("Open") && Execution.delayUntil({ bot.getProgress().get() == 130 }, 5000, 5200)
        } else {
            BresenhamPath.buildTo(gate)?.step() == true
        }
    }

    private fun chopTree() {
        Interaction.deselectItem()
        val tree = GameObjects.newQuery().names("Tree").results().nearest()
        if (tree != null && tree.turnAndInteract("Chop down")) {
            Execution.delayUntil({ Inventory.contains("Logs") || !tree.isValid }, { Players.getLocal()?.animationId != -1 }, 1200, 1800)
        }
    }

    private fun fish() {
        val fishingSpot = Npcs.newQuery().names("Fishing spot").results().nearest()
        val rawShrimpCount = Inventory.getQuantity("Raw shrimps")
        if (fishingSpot?.turnAndInteract("Net") == true) {
            Execution.delayUntil({ Inventory.getQuantity("Raw shrimps") > rawShrimpCount }, 10000)
        }
    }

    private fun lightFire() {
        Interaction.deselectItem()
        val tinderbox = Inventory.newQuery().names("Tinderbox").results().first()
        val logs = Inventory.newQuery().names("Logs").results().first()
        if (logs != null) {
            if (standingOnFire()) {
                BresenhamPath.buildTo(getEmptyCoordinate())?.step()
            } else if (logs.useOn(tinderbox)) {
                Execution.delayUntil({ !logs.isValid }, 1200, 1800)
            }
        } else chopTree()
    }

    private fun standingOnFire() = GameObjects.newQuery().names("Fire").on(Players.getLocal()?.position
            ?: Coordinate(-1, -1, -1)).results().isNotEmpty()

    private fun cook() {
        Interaction.deselectItem()

        val shrimp = Inventory.newQuery().names("Raw shrimps").results().first()
        val fire = GameObjects.newQuery().names("Fire").results().nearest()
        val rawShrimpCount = Inventory.getQuantity("Raw shrimps")

        if (shrimp.useOn(fire)) {
            Execution.delayUntil({ Inventory.getQuantity("Raw shrimps") < rawShrimpCount }, 1200, 1800)
        }
    }

    private fun getEmptyCoordinate(): Coordinate {
        val area = Area.Circular(Players.getLocal()?.position ?: Coordinate(-1, -1, -1), 10.0).coordinates
        return area.stream().filter { it.isReachable }.findAny().get()
    }
}
