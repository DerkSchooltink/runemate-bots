package bots.free.runespan

import bots.free.runespan.tasks.*
import bots.free.runespan.ui.RunespanUI
import com.runemate.game.api.client.ClientUI
import quantum.api.UIExtender
import quantum.api.core.ExtendedTaskBot
import quantum.api.framework.playersense.QuantumPlayerSense

class Runespan : ExtendedTaskBot() {

    val entityNames = mutableSetOf<String>()
    val creatureNames = mutableSetOf<String>()

    var currentLevel = -1

    override fun onStart(vararg strings: String) {
        setLoopDelay(750, 850)
        embeddableUI = UIExtender(this, RunespanUI(), "com/bots/free/runespan/ui/runespan.fxml")

        QuantumPlayerSense.initialize()
        ClientUI.showAlert("Do you want more experience and up to 4M GP per hour? Use <a href=\"https://www.runemate.com/botstore/search/Quantum+Abyss+Crafter\">Quantum Abyss Crafter</a>!")
        add(GrabKnowledge(), Siphon(), Loot(), EntityDeterminator(), GrabEssence())
    }

    fun addToEntityList(names: Set<String>) {
        entityNames.addAll(names)
    }

    fun addToCreatureList(names: Set<String>) {
        creatureNames.addAll(names)
    }
}
