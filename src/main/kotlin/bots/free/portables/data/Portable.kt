package bots.free.portables.data

enum class Portable(private val portableName: String, val actions: List<String>) {
    BRAZIER("Portable brazier", listOf("Add logs", "Add bones")),
    CRAFTER("Portable crafter", listOf("Craft", "Cut Gems", "Clay Crafting", "Tan Leather")),
    FLETCHER("Portable fletcher", listOf("Fletch", "Ammo", "String")),
    FORGE("Portable forge", listOf("Smith", "Smelt")),
    RANGE("Portable range", listOf("Cook")),
    WELL("Portable well", listOf("Take Vials", "Mix Potions"));

    override fun toString() = portableName

    companion object {
        val noProductPortables: Collection<Portable> = listOf(BRAZIER)
    }
}