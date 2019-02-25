package bots.premium.slayer.data

import quantum.api.game.combat.CombatStyle

enum class SlayerCombatStyle constructor(private val combatStyleName: String) {
    MELEE("Melee setup", 5, CombatStyle.MELEE),
    MAGIC("Magic setup", 3, CombatStyle.MAGIC),
    RANGE("Range setup", 4, CombatStyle.RANGED),
    DRAGON("Dragonfire setup", 2),
    WEREWOLF("Werewolf setup"),
    STRYKEWYRM("Strykewyrm setup"),
    ANCIENT("Ancient magics setup"),
    GARGOYLE("Gargoyle setup"),
    DESERT_LIZARD("Desert lizard setup"),
    MOGRE("Mogre setup"),
    SHADOW_CREATURE("Shadow creature setup");

    var presetNumber: Int = 5
    var combatStyle = CombatStyle.UNKNOWN

    constructor(combatStyleName: String, presetNumber: Int) : this(combatStyleName) {
        this.presetNumber = presetNumber
    }

    constructor(combatStyleName: String, presetNumber: Int, combatStyle: CombatStyle) : this(combatStyleName, presetNumber) {
        this.combatStyle = combatStyle
    }

    override fun toString() = combatStyleName

    companion object {
        fun isUsingRegularCombatStyle(slayerCombatStyle: SlayerCombatStyle?): Boolean {
            return slayerCombatStyle == MAGIC || slayerCombatStyle == MELEE || slayerCombatStyle == RANGE || slayerCombatStyle == DRAGON
        }
    }
}

