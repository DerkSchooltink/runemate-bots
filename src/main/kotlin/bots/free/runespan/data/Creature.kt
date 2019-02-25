package bots.free.runespan.data

enum class Creature constructor(override val entityName: String, override val level: Int) : RunespanEntity {
    MIND_ESSLING("Mind essling", 1),
    AIR_ESSLING("Air essling", 1),
    WATER_ESSLING("Water essling", 5),
    EARTH_ESSLING("Earth essling", 9),
    FIRE_ESSLING("Fire essling", 14),
    BODY_ESSHOUND("Body esshound", 20),
    COSMIC_ESSHOUND("Cosmic esshound", 27),
    CHAOS_ESSHOUND("Chaos esshound", 35),
    ASTRAL_ESSHOUND("Astral esshound", 40),
    NATURE_ESSHOUND("Nature esshound", 44),
    LAW_ESSHOUND("Law esshound", 54),
    DEATH_ESSWRAITH("Death esswraith", 65),
    BLOOD_ESSWRAITH("Blood esswraith", 77),
    SOUL_ESSWRAITH("Soul esswraith", 90)
}
