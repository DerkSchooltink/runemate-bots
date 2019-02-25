package quantum.api.framework.serialization.adapters

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import quantum.api.framework.serialization.parseElements
import com.runemate.game.api.hybrid.location.Area

/**
 * TypeAdapter for all types of areas
 *
 * The serialized object will always have a field "type" alongside with attributes relevant to the respective implementation
 * The reason to have only one TypeAdapter for all Areas is to allow polymorphism.
 */
object AreaTypeAdapter : TypeAdapter<Area>() {
    override fun write(writer: JsonWriter, area: Area?): Unit = writer.run {
        if (area == null) {
            nullValue()
        } else {
            beginObject()
            name("type").value(area.javaClass.simpleName)
            when (area) {
                is Area.Rectangular -> {
                    name("bottomLeft"); CoordinateTypeAdapter.write(this, area.bottomLeft)
                    name("topRight"); CoordinateTypeAdapter.write(this, area.topRight)
                }
                is Area.Circular -> {
                    name("center"); CoordinateTypeAdapter.write(this, area.center)
                    name("radius").value(area.radius)
                }
                is Area.Absolute -> {
                    name("coordinates")
                    beginArray()
                    area.coordinates.forEach { CoordinateTypeAdapter.write(this, it) }
                    endArray()
                }
                is Area.Polygonal -> {
                    name("vertices")
                    beginArray()
                    area.vertices.forEach { CoordinateTypeAdapter.write(this, it) }
                    endArray()
                }
            }
            endObject()
        }
    }

    override fun read(reader: JsonReader): Area? = reader.run {
        if (peek() == JsonToken.NULL) {
            nextNull()
            null
        } else {
            beginObject()
            nextName()
            when (nextString()) {
                className<Area.Rectangular>() -> {
                    val (bottomLeft, topRight) = parseElements("bottomLeft", "topRight")
                    Area.Rectangular(CoordinateTypeAdapter.fromJson(bottomLeft!!), CoordinateTypeAdapter.fromJson(topRight!!))
                }
                className<Area.Circular>() -> {
                    val (center, radius) = parseElements("center", "radius")
                    Area.Circular(CoordinateTypeAdapter.fromJson(center!!), radius!!.asDouble)
                }
                className<Area.Absolute>() -> {
                    val (coordinates) = parseElements("coordinates")
                    coordinates!!.asJsonArray.map(CoordinateTypeAdapter::fromJson).let(Area::Absolute)
                }
                className<Area.Polygonal>() -> {
                    val (coordinates) = parseElements("vertices")
                    coordinates!!.asJsonArray.map(CoordinateTypeAdapter::fromJson).let {
                        Area.Polygonal(*it.toTypedArray())
                    }
                }
                else -> {
                    parseElements()
                    null
                }
            }.also {
                endObject()
            }
        }
    }

    private inline fun <reified T> className(): String = T::class.java.simpleName
}