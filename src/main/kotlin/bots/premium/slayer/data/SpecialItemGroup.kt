package bots.premium.slayer.data

import bots.premium.slayer.data.SpecialItem.*

enum class SpecialItemGroup(private var groupName: String, vararg specialItems: SpecialItem) {
    SLAYER_HELM("Slayer helm", SLAYER_HELMET, FULL_SLAYER_HELMET, REINFORCED_SLAYER_HELMET, STRONG_SLAYER_HELMET, MIGHTY_SLAYER_HELMET),
    NOSEPEGS("Nose peg", *SLAYER_HELM.specialItems.toTypedArray(), NOSEPEG),
    MIRROR("Mirror shield", MIRROR_SHIELD, MASK_OF_STONE, MASK_OF_REFLECTION, HELM_OF_LITTLE_KINGS, HELM_OF_PETRIFICATION),
    LEAF("Leaf bladed & broad", LEAF_BLADED_SPEAR, LEAF_BLADED_SWORD, BROAD_ARROWS, BROAD_TIPPED_BOLTS),
    WYVERN("Wyvern", DRAGONFIRE_DEFLECTOR, DRAGONFIRE_SHIELD, DRAGONFIRE_WARD, ANTI_DRAGON_SHIELD, ELEMENTAL_SHIELD, MIND_SHIELD, BODY_SHIELD, COSMIC_SHIELD, CHAOS_SHIELD),
    MASK("Facemask", FACE_MASK, MASKED_EARMUFFS, HELM_OF_DEVILRY, *SLAYER_HELM.specialItems.map { it }.toTypedArray());

    var specialItems: List<SpecialItem> = specialItems.toList()

    override fun toString() = groupName
}