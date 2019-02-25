package bots.premium.slayer.data

import com.runemate.game.api.hybrid.local.Skill
import com.runemate.game.api.hybrid.location.navigation.web.requirements.SkillRequirement

enum class SpecialItem(var specialItemName: String) {
    SLAYER_HELMET("Slayer helmet", SkillRequirement(Skill.SLAYER, 35), SkillRequirement(Skill.DEFENCE, 20)),
    FULL_SLAYER_HELMET("Full slayer helmet", SkillRequirement(Skill.DEFENCE, 10)),
    REINFORCED_SLAYER_HELMET("Reinforced slayer helmet", SkillRequirement(Skill.SLAYER, 30), SkillRequirement(Skill.DEFENCE, 30)),
    STRONG_SLAYER_HELMET("Strong slayer helmet", SkillRequirement(Skill.SLAYER, 70), SkillRequirement(Skill.DEFENCE, 70)),
    MIGHTY_SLAYER_HELMET("Mighty slayer helmet", SkillRequirement(Skill.SLAYER, 70), SkillRequirement(Skill.DEFENCE, 70)),
    CORRUPTED_SLAYER_HELMET("Corrupted slayer helmet", SkillRequirement(Skill.SLAYER, 85), SkillRequirement(Skill.DEFENCE, 70)),
    NOSEPEG("Nosepeg", SkillRequirement(Skill.SLAYER, 60)),
    MIRROR_SHIELD("Mirror shield", SkillRequirement(Skill.SLAYER, 25), SkillRequirement(Skill.DEFENCE, 20)),
    MASK_OF_STONE("Mask of stone", SkillRequirement(Skill.DEFENCE, 20)),
    HELM_OF_LITTLE_KINGS("Helm of Little Kings", SkillRequirement(Skill.DEFENCE, 30)),
    HELM_OF_PETRIFICATION("Helm of Petrification", SkillRequirement(Skill.DEFENCE, 20)),
    MASK_OF_REFLECTION("Mask of Reflection", SkillRequirement(Skill.DEFENCE, 30)),
    ANTI_DRAGON_SHIELD("Anti-dragon shield"),
    DRAGONFIRE_SHIELD("Dragonfire shield", SkillRequirement(Skill.DEFENCE, 70)),
    DRAGONFIRE_WARD("Dragonfire ward", SkillRequirement(Skill.DEFENCE, 70)),
    DRAGONFIRE_DEFLECTOR("Dragonfire deflector", SkillRequirement(Skill.DEFENCE, 70)),
    SLAYER_GLOVES("Slayer gloves", SkillRequirement(Skill.SLAYER, 42)),
    BUG_LANTERN("Lit bug lantern", SkillRequirement(Skill.SLAYER, 33)),
    LEAF_BLADED_SPEAR("Leaf-bladed spear", SkillRequirement(Skill.SLAYER, 55), SkillRequirement(Skill.ATTACK, 50)),
    LEAF_BLADED_SWORD("Leaf-bladed sword", SkillRequirement(Skill.SLAYER, 55), SkillRequirement(Skill.ATTACK, 50)),
    BROAD_ARROWS("Broad arrows", SkillRequirement(Skill.SLAYER, 55), SkillRequirement(Skill.RANGED, 50)),
    BROAD_TIPPED_BOLTS("Broad-tipped bolts", SkillRequirement(Skill.SLAYER, 55), SkillRequirement(Skill.RANGED, 50)),
    ELEMENTAL_SHIELD("Elemental shield"),
    MIND_SHIELD("Mind shield", SkillRequirement(Skill.MAGIC, 20)),
    BODY_SHIELD("Body shield", SkillRequirement(Skill.MAGIC, 35)),
    COSMIC_SHIELD("Cosmic shield", SkillRequirement(Skill.MAGIC, 40)),
    CHAOS_SHIELD("Chaos shield", SkillRequirement(Skill.MAGIC, 50)),
    HOLY_SYMBOL("Holy symbol"),
    LIGHT_SOURCE("Bullseye lantern"),
    FACE_MASK("Face mask", SkillRequirement(Skill.SLAYER, 10)),
    MASKED_EARMUFFS("Masked earmuffs"),
    HELM_OF_DEVILRY("Helm of Devilry", SkillRequirement(Skill.DEFENCE, 40)),
    WITCHWOOD_ICON("Witchwood icon", SkillRequirement(Skill.SLAYER, 35)),
    INSULATED_BOOTS("Insulated boots", SkillRequirement(Skill.SLAYER, 37)),
    MAAT_FEATHER("Feather of Ma'at");

    private lateinit var requirement: List<SkillRequirement>

    constructor(specialItemName: String, vararg requirements: SkillRequirement) : this(specialItemName) {
        this.specialItemName = specialItemName
        this.requirement = requirements.toList()
    }
}
