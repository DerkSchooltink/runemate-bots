package bots.premium.slayer.data

import bots.premium.slayer.frame.Requirements
import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate
import com.runemate.game.api.hybrid.util.collections.Pair

enum class Monster constructor(val monsterName: String, val requirements: Requirements, val location: Coordinate) : Locatable {
    ABERRANT_SPECTRE("Aberrant spectre", Requirements(SlayerCombatStyle.RANGE, SpecialItemGroup.NOSEPEGS), Coordinate(3442, 3557, 1)),
    ABYSSAL_DEMON("Abyssal demon", Requirements(SlayerCombatStyle.MELEE), Coordinate(3408, 3544, 2)),
    ACHERON_MAMMOTH("Acheron mammoth", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    ADAMANT_DRAGON("Adamant dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(4510, 6028, 0)),
    AIRUT("Airut", Requirements(SlayerCombatStyle.MELEE), Coordinate(2275, 3611, 0)),
    ANKOU("Ankou", Requirements(SlayerCombatStyle.MELEE), Coordinate(2326, 5199, 0)),
    AQUANITE("Aquanite", Requirements(SlayerCombatStyle.RANGE), Coordinate(2715, 9973, 0)),
    ASCENSION("Rorarius", Requirements(SlayerCombatStyle.RANGE), Coordinate(1110, 595, 1)),
    AVIANSIE("Aviansie", Requirements(SlayerCombatStyle.RANGE), Coordinate(2872, 5272, 0), Pair<IntArray, IntArray>(intArrayOf(50, -275, 20), intArrayOf(-75, -300, 0))),
    BANSHEE("Banshee", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.SLAYER_HELM), Coordinate(3440, 3550, 0)),
    BASILISK("Basilisk", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.MIRROR), Coordinate(2742, 10010, 0)),
    BAT("Bat", Requirements(SlayerCombatStyle.MELEE), Coordinate(3373, 3487, 0)),
    BEAR("Black bear", Requirements(SlayerCombatStyle.MELEE), Coordinate(2705, 3337, 0)),
    BIRD("Bird", Requirements(SlayerCombatStyle.MELEE), Coordinate(3207, 3283, 0)),
    BLACK_DEMON("Black demon", Requirements(SlayerCombatStyle.RANGE), Coordinate(2866, 9778, 0)),
    BLACK_DRAGON("Black dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2833, 9826, 0)),
    BLOODVELD("Bloodveld", Requirements(SlayerCombatStyle.RANGE), Coordinate(3420, 3564, 1)),
    BLUE_DRAGON("Blue dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2906, 9801, 0)),
    BRINE_RAT("Brine rat", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    BRONZE_DRAGON("Bronze dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2735, 9487, 0)),
    CAMEL_WARRIORS("Camel warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    CATABLEPON("Catablepon", Requirements(SlayerCombatStyle.MELEE), Coordinate(2153, 5253, 0)),
    CAVE_BUG("Cave bug", Requirements(SlayerCombatStyle.MELEE), Coordinate(2213, 4548, 0)),
    CAVE_CRAWLER("Cave crawler", Requirements(SlayerCombatStyle.MELEE), Coordinate(2787, 9997, 0)),
    CAVE_HORROR("Cave horror", Requirements(
        SlayerCombatStyle.MELEE,
        SpecialItem.LIGHT_SOURCE,
        SpecialItem.WITCHWOOD_ICON
    ), Coordinate(0, 0, 0)),
    CAVE_SLIME("Cave slime", Requirements(SlayerCombatStyle.MELEE), Coordinate(3163, 9591, 0)),
    CELESTIAL_DRAGON("Celestial dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2269, 5978, 0)),
    CHAOS_GIANT("Chaos giant", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    COCKATRICE("Cockatrice", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.MIRROR), Coordinate(2790, 10035, 0)),
    CORRUPTED_CREATURES("Corrupted scorpion", Requirements(SlayerCombatStyle.MAGIC, SpecialItem.MAAT_FEATHER), Coordinate(2385, 6817, 3)),
    COW("Cow", Requirements(SlayerCombatStyle.MELEE), Coordinate(3175, 3319, 0)),
    CRAWLING_HAND("Crawling hand", Requirements(SlayerCombatStyle.MELEE), Coordinate(2218, 4578, 0)),
    CRES_CREATION("Cres's creation", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    CROCODILE("Crocodiles", Requirements(SlayerCombatStyle.RANGE), Coordinate(3299, 2921, 0)),
    CRYSTAL_SHIFTERS("Crystal Shapeshifter", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    CYCLOPS("Cyclope", Requirements(SlayerCombatStyle.RANGE), Coordinate(2620, 3277, 0)),
    DAGANNOTH("Dagannoth", Requirements(SlayerCombatStyle.MELEE), Coordinate(2446, 10147, 0)),
    DARK_BEAST("Dark beast", Requirements(SlayerCombatStyle.MELEE), Coordinate(2003, 4647, 0)),
    DESERT_LIZARD("Desert lizard", Requirements(SlayerCombatStyle.DESERT_LIZARD), Coordinate(3390, 3067, 0)),
    DESERT_STRYKEWYRM("Desert strykewyrm", Requirements(SlayerCombatStyle.STRYKEWYRM), Coordinate(3363, 3161, 0)),
    DOG("Dog", Requirements(SlayerCombatStyle.MELEE), Coordinate(3400, 2994, 0)),
    DUST_DEVIL("Dust devil", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.MASK), Coordinate(3215, 9357, 0)),
    DWARF("Dwarf", Requirements(SlayerCombatStyle.MELEE), Coordinate(3020, 3457, 0)),
    EARTH_WARRIOR("Earth warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(3120, 9969, 0)),
    EDDIMU("Eddimu", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    ELVES("Elf warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(2201, 3325, 1)),
    FEVER_SPIDER("Fever spide", Requirements(SlayerCombatStyle.MELEE, SpecialItem.SLAYER_GLOVES), Coordinate(0, 0, 0)),
    FIRE_GIANTS("Fire giant", Requirements(SlayerCombatStyle.MELEE), Coordinate(2658, 9482, 0)),
    FLESH_CRAWLER("Flesh crawler", Requirements(SlayerCombatStyle.MELEE), Coordinate(2042, 5189, 0)),
    FUNGAL_MAGI("Fungal magi", Requirements(SlayerCombatStyle.MELEE), Coordinate(4654, 5476, 3)),
    GANODERMIC("Ganodermic creature", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    GARGOYLE("Gargoyle", Requirements(SlayerCombatStyle.GARGOYLE), Coordinate(3440, 3566, 2)),
    GELATINOUS("Gelatinous abomination", Requirements(SlayerCombatStyle.MELEE), Coordinate(2216, 4519, 0)),
    GHOST("Ghost", Requirements(SlayerCombatStyle.MELEE), Coordinate(2889, 9848, 0)),
    GHOUL("Ghoul", Requirements(SlayerCombatStyle.MELEE), Coordinate(3494, 3537, 0)),
    GLACORS("Glacor", Requirements(SlayerCombatStyle.MAGIC), Coordinate(0, 0, 0)),
    GOBLIN("Goblin", Requirements(SlayerCombatStyle.MELEE), Coordinate(3258, 3240, 0)),
    GORAK("Gorak", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    GREATER_DEMON("Greater demon", Requirements(SlayerCombatStyle.RANGE), Coordinate(2638, 9506, 2)),
    GREEN_DRAGON("Green dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(0, 0, 0)),
    GRIFOPALINES("Grifopaline", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    GRIFOLAROOS("Grifolaroo", Requirements(SlayerCombatStyle.MAGIC), Coordinate(4647, 5486, 2)),
    GROTWORMS("Grotworm", Requirements(SlayerCombatStyle.MELEE), Coordinate(1188, 6499, 0), Coordinate(1168, 6368, 0)),
    HARPIE_BUG_SWARM("Harpie bug swarm", Requirements(SlayerCombatStyle.MELEE, SpecialItem.BUG_LANTERN), Coordinate(2861, 3111, 0)),
    HELLHOUND("Hellhound", Requirements(SlayerCombatStyle.MELEE), Coordinate(2857, 9842, 0)),
    HILL_GIANT("Hill giant", Requirements(SlayerCombatStyle.MELEE), Coordinate(3108, 9832, 0)),
    HOBGOBLIN("Hobgoblin", Requirements(SlayerCombatStyle.MELEE), Coordinate(2914, 3274, 0)),
    ICE_GIANT("Ice giant", Requirements(SlayerCombatStyle.MELEE), Coordinate(3059, 9577, 0)),
    ICE_STRYKEWYRM("Ice strykewyrm", Requirements(SlayerCombatStyle.STRYKEWYRM), Coordinate(0, 0, 0)),
    ICE_WARRIOR("Ice warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(3059, 9577, 0)),
    ICEFIEND("Icefiend", Requirements(SlayerCombatStyle.MELEE), Coordinate(3005, 3476, 0)),
    INFERNAL_MAGE("Infernal mage", Requirements(SlayerCombatStyle.MELEE), Coordinate(3410, 3566, 1)),
    IRON_DRAGON("Iron dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2721, 9439, 0)),
    JELLY("Jelly", Requirements(SlayerCombatStyle.MELEE), Coordinate(2708, 10028, 0)),
    JUNGLE_HORROR("Jungle horror", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    JUNGLE_STRYKEWYRM("Jungle strykewyrm", Requirements(SlayerCombatStyle.STRYKEWYRM), Coordinate(2459, 2908, 0)),
    KALGERION_DEMONS("Kal'gerion demon", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    KALPHITE("Exiled kalphite marauder", Requirements(SlayerCombatStyle.RANGE), Coordinate(2953, 1637, 0)),
    KILLERWATT("Killerwatt", Requirements(SlayerCombatStyle.MELEE, SpecialItem.INSULATED_BOOTS), Coordinate(0, 0, 0)),
    KURASK("Kurask", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.LEAF), Coordinate(2705, 9999, 0)),
    LAVA_STRYKEWYRM("Lava strykewyrm", Requirements(SlayerCombatStyle.STRYKEWYRM), Coordinate(0, 0, 0)),
    LESSER_DEMON("Lesser demon", Requirements(SlayerCombatStyle.MELEE), Coordinate(2930, 9804, 0)),
    LIVING_ROCK("Living rock striker", Requirements(SlayerCombatStyle.MELEE), Coordinate(3655, 5121, 0)),
    LIVING_WYVERN("Living wyvern", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.WYVERN), Coordinate(0, 0, 0)),
    MINOTAUR("Minotaur", Requirements(SlayerCombatStyle.MELEE), Coordinate(1871, 5237, 0)),
    MITHRIL_DRAGON("Mithril dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(1778, 5343, 1)),
    MOGRE("Mogre", Requirements(SlayerCombatStyle.MOGRE), Coordinate(2985, 3112, 0)),
    MOLANISK("Molanisk", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    MONKEY("Monkey", Requirements(SlayerCombatStyle.MELEE), Coordinate(2894, 3078, 0)),
    MOSS_GIANT("Moss giant", Requirements(SlayerCombatStyle.MELEE), Coordinate(2555, 3407, 0)),
    MUSPAH("Muspah", Requirements(SlayerCombatStyle.ANCIENT), Coordinate(0, 0, 0)),
    MUTATED_JADINKO("Mutated jadinko baby", Requirements(SlayerCombatStyle.MELEE), Coordinate(3021, 9255, 0)),
    MUTATED_ZYGOMITE("Mutated zygomite", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    NECHRYAEL("Nechryael", Requirements(SlayerCombatStyle.MELEE), Coordinate(3406, 3571, 2)),
    NIGHTMARE_CREATURES("Nightmare creature", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    NIHIL("Nihil", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    OGRE("Ogre", Requirements(SlayerCombatStyle.MELEE), Coordinate(2508, 2854, 0)),
    OTHERWORLDLY_BEINGS("Otherworldly being", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    PIGS("Pig", Requirements(SlayerCombatStyle.MELEE), Coordinate(1124, 5788, 0)),
    POLYPORE_CREATURES("Polypore creature", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    PYREFIEND("Pyrefiend", Requirements(SlayerCombatStyle.MELEE), Coordinate(2761, 10008, 0)),
    RAT("Rat", Requirements(SlayerCombatStyle.MELEE), Coordinate(3269, 3354, 0)),
    RED_DRAGON("Red dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2703, 9509, 0)),
    RIPPER_DEMON("Ripper demon", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    ROCK_SLUG("Rock slug", Requirements(SlayerCombatStyle.MELEE), Coordinate(2798, 10019, 0)),
    RUNE_DRAGONS("Rune dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(0, 0, 0)),
    SCARABS("Scabarite", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    SCORPION("Scorpion", Requirements(SlayerCombatStyle.MELEE), Coordinate(3300, 3309, 0)),
    SEA_SNAKE("Sea snake", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    SHADE("Shade", Requirements(SlayerCombatStyle.MELEE), Coordinate(2361, 5213, 0)),
    SHADOW_CREATURES("Blissful shadow", Requirements(SlayerCombatStyle.MAGIC), Coordinate(2178, 3385, 1)),
    SHADOW_WARRIORS("Shadow warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    SKELETAL_WYVERN("Skeletal wyvern", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.WYVERN), Coordinate(0, 0, 0)),
    SKELETON("Skeleton", Requirements(SlayerCombatStyle.MELEE), Coordinate(3096, 9892, 0)),
    SPIDER("Spider", Requirements(SlayerCombatStyle.MELEE), Coordinate(3188, 3185, 0)),
    SPIRITUAL_MAGE("Spiritual mage", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    SPIRITUAL_RANGER("Spiritual ranger", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    SPIRITUAL_WARRIOR("Spiritual warrior", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    STEEL_DRAGON("Steel dragon", Requirements(SlayerCombatStyle.DRAGON), Coordinate(2721, 9439, 0)),
    SUQAH("Suqah", Requirements(SlayerCombatStyle.MELEE), Coordinate(2130, 3949, 0)),
    TERROR_DOG("Terror dog", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    TORMENTED_DEMON("Tormented demon", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    TROLLS("Troll", Requirements(SlayerCombatStyle.MELEE), Coordinate(2227, 4380, 0)),
    TUROTH("Turoth", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.LEAF), Coordinate(2722, 10010, 0)),
    TZHAAR("TzHaar-Mej", Requirements(SlayerCombatStyle.MELEE), Coordinate(4684, 5119, 0)),
    VAMPIRE("Vampyre", Requirements(SlayerCombatStyle.MELEE), Coordinate(3582, 3487, 0)),
    VOLCANIC_CREATURES("Volcanic creature", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    VYREWATCH("Vyrewatch", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    WALL_BEAST("Wall beast", Requirements(SlayerCombatStyle.MELEE, SpecialItemGroup.SLAYER_HELM), Coordinate(0, 0, 0)),
    WARPED_TERRORBIRD("Warped terrorbird", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    WARPED_TORTOISE("Warped tortoise", Requirements(SlayerCombatStyle.MELEE), Coordinate(0, 0, 0)),
    WATERFIEND("Waterfiend", Requirements(SlayerCombatStyle.RANGE), Coordinate(1763, 5360, 0)),
    WEREWOLF("Werewolf", Requirements(SlayerCombatStyle.WEREWOLF), Coordinate(2464, 2872, 0)),
    WOLF("Wolf", Requirements(SlayerCombatStyle.MELEE), Coordinate(2866, 3449, 0)),
    ZOMBIE("Zombie", Requirements(SlayerCombatStyle.MELEE), Coordinate(3143, 9902, 0));

    var lowLevelCoordinate: Coordinate? = null
    var forcedModel: Pair<IntArray, IntArray>? = null

    constructor(name: String, requirements: Requirements, location: Coordinate, lowLevelCoordinate: Coordinate) : this(name, requirements, location) {
        this.lowLevelCoordinate = lowLevelCoordinate
    }

    constructor(name: String, requirements: Requirements, location: Coordinate, forcedModel: Pair<IntArray, IntArray>) : this(name, requirements, location) {
        this.forcedModel = forcedModel
    }

    override fun toString() = monsterName

    override fun getHighPrecisionPosition() = Coordinate.HighPrecision(location.x, location.y, location.plane)

    override fun getArea(): Area.Rectangular = location.area

    override fun getPosition() = location

    companion object {
        val unsupportedMonsters = values().filter { it.location == Coordinate(0, 0, 0) }
    }
}
