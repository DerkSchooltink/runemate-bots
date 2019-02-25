package bots.free.runespan.data

enum class Node constructor(override val entityName: String, override val level: Int) : RunespanEntity {
    CYCLONE("Cyclone", 1),
    MIND_STORM("Mind storm", 1),
    WATER_POOL("Water pool", 5),
    ROCK_FRAGMENT("Rock fragment", 9),
    VINE("Vine", 17),
    FIRE_STORM("Fire storm", 27),
    FIREBALL("Fireball", 14),
    FLESHY_GROWTH("Fleshy growth", 20),
    JUMPER("Jumper", 54),
    SKULLS("Skulls", 65),
    CHAOTIC_CLOUD("Chaotic cloud", 35),
    BLOOD_POOL("Blood pool", 77),
    SHIFTER("Shifter", 44),
    NEBULA("Nebula", 40),
    LIVING_SOUL("Living soul", 90),
    BLOODY_SKULLS("Bloody skulls", 83),
    UNDEAD_SOUL("Undead soul", 95)
}
