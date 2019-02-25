package quantum.api.framework.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import quantum.api.framework.utils.ComponentMap

/**
 * Creates a ComponentMap of JsonElements for an elegant way of parsing json objects
 *
 * Note that this method is greedy and will parse the rest of the current json object's fields.
 */
fun JsonReader.parseElements(vararg names: String) = object : ComponentMap<String, JsonElement?>(*names) {
    val elements: MutableMap<String, JsonElement?> = mutableMapOf()

    init {
        while (hasNext()) {
            elements[nextName()] = JsonParser().parse(this@parseElements)
        }
    }

    override fun map(key: String): JsonElement? = elements[key]
}