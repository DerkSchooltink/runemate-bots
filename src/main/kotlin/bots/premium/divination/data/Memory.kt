package bots.premium.divination.data

import com.runemate.game.api.hybrid.local.Skill

enum class Memory(private val memoryName: String, val divinationLevel: Int) {
    FADED("Faded memories", 70),
    LUSTROUS("Lustrous memories", 70),
    BRILLIANT("Brilliant memories", 80),
    RADIANT("Radiant memories", 85),
    LUMINOUS("Luminous memories", 90),
    INCANDESCENT("Incandescent memories", 95);

    override fun toString() = memoryName

    companion object {
        fun harvestableMemories() = values().filter { Skill.DIVINATION.baseLevel >= it.divinationLevel }
        fun getConstant(value: String) = values().firstOrNull { it.memoryName == value } ?: LUSTROUS
    }
}