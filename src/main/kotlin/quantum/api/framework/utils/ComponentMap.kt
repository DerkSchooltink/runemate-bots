package quantum.api.framework.utils

/**
 * Class for mapping keys to values and being able to retrieve them as variables via component destructuring
 *
 * This class can also be used to declare and initialize multiple variables in one line
 *
 * Example:
 * val ages = mapOf("Elliot" to 23, "Koen" to 21, "Torben" to 20)
 * val (elliotAge, koenAge, torbenAge) = ComponentMap.by("Elliot", "Koen", "Torben") {
 *     ages[it]
 * }
 *
 * val (x, y, z) = ComponentMap.by(23, 44, 0)
 */
abstract class ComponentMap<in K, out V>(private vararg val keys: K) : ComponentDelegate<V> {

    protected abstract fun map(key: K): V

    override fun getComponent(index: Int): V = map(keys[index - 1])

    companion object {
        private fun <K, V> by(vararg keys: K, mapper: (K) -> V) = object : ComponentMap<K, V>(*keys) {
            override fun map(key: K): V = mapper(key)
        }

        fun <T> by(vararg values: T) = by(*values, mapper = { it })
    }
}