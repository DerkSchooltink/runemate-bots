package quantum.api.framework.serialization.adapters

import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import quantum.api.framework.serialization.parseElements
import com.runemate.game.api.hybrid.location.Coordinate

/**
 * TypeAdapter for Coordinates
 *
 * For sake of readability, this TypeAdapter will create JSON fields for each coordinate parameter, as opposed to the
 * Coordinate class only having one UID field.
 */
object CoordinateTypeAdapter : TypeAdapter<Coordinate>() {
    override fun write(writer: JsonWriter, c: Coordinate?): Unit = writer.run {
        if (c == null) {
            nullValue()
        } else {
            beginObject()
            name("x").value(c.x)
            name("y").value(c.y)
            name("plane").value(c.plane)
            endObject()
        }
    }

    override fun read(reader: JsonReader): Coordinate? = reader.run {
        if (peek() == JsonToken.NULL) {
            nextNull()
            null
        } else {
            beginObject()
            val (x, y, plane) = parseElements("x", "y", "plane")
            Coordinate(x!!.asInt, y!!.asInt, plane!!.asInt).also {
                endObject()
            }
        }
    }

    fun fromJson(json: JsonElement) = json.asJsonObject.run {
        Coordinate(get("x").asInt, get("y").asInt, get("plane").asInt)
    }
}