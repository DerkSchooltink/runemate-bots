package quantum.api.game.data

enum class Food(private val foodName: String) {
    SHARK("Shark"),
    LOBSTER("Lobster");

    override fun toString() = foodName
}