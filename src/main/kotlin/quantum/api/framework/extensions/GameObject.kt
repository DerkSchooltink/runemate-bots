package quantum.api.framework.extensions

import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.util.collections.Pair
import java.util.*

fun GameObject?.setForcedModel(model: HashMap<IntArray, IntArray>?): GameObject? {
    if (model != null) {
        val left = model.keys.first()
        val right = model.values.first()
        this?.setForcedModel(Pair(left, right))
    }
    return this
}