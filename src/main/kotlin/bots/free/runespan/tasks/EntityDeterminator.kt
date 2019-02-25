package bots.free.runespan.tasks

import bots.free.runespan.Runespan
import bots.free.runespan.data.Creature
import bots.free.runespan.data.Node
import bots.free.runespan.data.RunespanEntity
import quantum.api.core.ExtendedTask
import com.runemate.game.api.hybrid.RuneScape
import com.runemate.game.api.hybrid.local.Skill
import java.util.*

class EntityDeterminator : ExtendedTask<Runespan>() {

    override fun validate() = RuneScape.isLoggedIn() && bot.currentLevel < Skill.RUNECRAFTING.currentLevel

    override fun execute() {
        logger.info("Determining optimal nodes to siphon...")
        getOptimalRunespanCreatures()
        bot.currentLevel = Skill.RUNECRAFTING.currentLevel
    }

    private fun getOptimalRunespanCreatures() {
        val nodeList = mutableSetOf<String>()
        val creatureList = mutableSetOf<String>()
        val totalList = mutableSetOf<RunespanEntity>()

        Collections.addAll(totalList, *Creature.values())
        Collections.addAll(totalList, *Node.values())

        totalList.stream().filter { it.level <= Skill.RUNECRAFTING.currentLevel }.forEach {
            when (it) {
                is Node -> nodeList.add(it.entityName)
                is Creature -> creatureList.add(it.entityName)
            }
        }

        bot.addToCreatureList(creatureList)
        bot.addToEntityList(nodeList)
    }
}
