package bots.premium.tutorialisland.task

import bots.premium.tutorialisland.TutorialIsland
import bots.premium.tutorialisland.data.Rock
import quantum.api.core.ExtendedTask
import quantum.api.game.PathBuilder
import quantum.api.game.turnAndInteract
import com.runemate.game.api.hybrid.local.Varp
import com.runemate.game.api.hybrid.local.hud.Menu
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath
import com.runemate.game.api.hybrid.region.GameObjects
import com.runemate.game.api.hybrid.region.Players
import com.runemate.game.api.hybrid.util.calculations.Distance
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab
import com.runemate.game.api.script.Execution

class MiningHandler : ExtendedTask<TutorialIsland>() {

    private val miningCoordinate = Coordinate(3076, 9497, 0)
    private val miningInstructorCoordinate = Coordinate(3080, 9505, 0)

    override fun validate(): Boolean {
        logger.debug(bot.getProgress().get())
        return Varp(406).value.let { it == 8 || it == 9 }
    }

    override fun execute() {
        ChatDialog.getContinue()?.select()
        when (bot.getProgress().get()) {
            260 -> bot.talkToInstructor("Mining Instructor", miningInstructorCoordinate)
            270, 280 -> prospect(Rock.COPPER)
            281, 271 -> prospect(Rock.TIN)
            291, 290 -> bot.talkToInstructor("Mining Instructor", miningInstructorCoordinate)
            300 -> mine(Rock.COPPER)
            311 -> mine(Rock.TIN)
            320 -> smelt()
            330 -> bot.talkToInstructor("Mining Instructor", miningInstructorCoordinate)
            340 -> smith()
            350 -> smith()
            360 -> handleGate()
        }
    }

    private fun handleGate() {
        GameObjects.newQuery().names("Gate").actions("Open").results().nearest().let {
            if (it != null && it.isVisible) {
                if (it.turnAndInteract("Open")) {
                    Execution.delayWhile { Players.getLocal()?.isMoving }
                } else it.turnAndInteract("Open")
            } else {
                RegionPath.buildTo(Coordinate(3091, 9503, 0))?.step()
            }
        }
    }

    private fun smith() {
        getDaggerWidget()?.let {
            if (it.isVisible && it.interact("Smith 1")) {
                Execution.delayUntil({ Inventory.contains("Bronze dagger") }, 1200, 1800)
            }
        }

        if (!ControlPanelTab.INVENTORY.isOpen) ControlPanelTab.INVENTORY.open()

        val anvil = GameObjects.newQuery().names("Anvil").results().nearest()
        val bronzeBar = Inventory.newQuery().names("Bronze bar").results().first()

        if (Distance.to(miningCoordinate) > 7) {
            RegionPath.buildTo(miningCoordinate)?.step()
        } else {
            if (Inventory.getSelectedItem() != bronzeBar) {
                if (bronzeBar?.interact("Use") == true && anvil?.turnAndInteract("Use") == true) {
                    Execution.delayUntil({ getDaggerWidget()?.isVisible == true }, 1200, 1800)
                }
            } else if (anvil?.turnAndInteract("Use") == true) {
                Execution.delayUntil({ getDaggerWidget()?.isVisible == true }, 1200, 1800)
            }
        }
    }

    private fun smelt() {
        val tinOre = Inventory.newQuery().names("Tin ore").results().first()
        val furnace = GameObjects.newQuery().names("Furnace").results().nearest()

        if (furnace?.isVisible == false) {
            PathBuilder.buildTo(furnace)?.step()
        } else {
            if (Inventory.getSelectedItem() != tinOre) {
                if (tinOre?.interact("Use") == true && furnace?.turnAndInteract("Use") == true) {
                    Execution.delayUntil({ Inventory.contains("Bronze bar") }, 5000)
                }
            } else if (furnace?.turnAndInteract("Use") == true) Execution.delayUntil({ Inventory.contains("Bronze bar") }, 5000)
        }
    }

    private fun prospect(rock: Rock) {
        if (Menu.isOpen()) Menu.close()
        val prospectableRock = rock.getClosestWithOre()
        if (Distance.to(prospectableRock) < 3) {
            if (prospectableRock?.turnAndInteract("Prospect") == true) {
                Execution.delayUntil({ bot.pendingContinue() }, 6000, 6600)
            }
        } else PathBuilder.buildTo(prospectableRock)?.step()
    }

    private fun mine(rock: Rock) {
        val minableOre = rock.getClosestWithOre()
        if (Distance.to(minableOre) < 3) {
            if (minableOre?.turnAndInteract("Mine") == true) {
                Execution.delayUntil({ bot.pendingContinue() }, 6000, 6600)
            }
        } else PathBuilder.buildTo(minableOre)?.step()
    }

    private fun getDaggerWidget() = Interfaces.newQuery().texts("Dagger").containers(312).results().first()
}