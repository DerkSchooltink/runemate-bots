package quantum.api.framework.serialization.adapters

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.runemate.game.api.hybrid.location.Area
import com.runemate.game.api.hybrid.location.Coordinate

object RuneMateTypeAdapterFactory : TypeAdapterFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? = when {
        type.isAssignableTo<Coordinate>() -> CoordinateTypeAdapter
        type.isAssignableTo<Area>() -> AreaTypeAdapter
        else -> null
    } as? TypeAdapter<T>

    private inline fun <reified T> TypeToken<*>.isAssignableTo() = T::class.java.isAssignableFrom(rawType)
}