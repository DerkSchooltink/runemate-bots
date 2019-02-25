package quantum.api.framework.extensions

import com.runemate.game.api.hybrid.entities.Actor
import com.runemate.game.api.hybrid.queries.ActorQueryBuilder
import com.runemate.game.api.hybrid.queries.QueryBuilder

fun <T : Actor, QB : QueryBuilder<*, *, *>> ActorQueryBuilder<T, QB>.names(names: List<String>?): QB =
        names(*(names?.toTypedArray() ?: emptyArray()))

fun <T : Actor, QB : QueryBuilder<*, *, *>> ActorQueryBuilder<T, QB>.notPerformingAnimations(animations: List<Int>?): QB =
        filter { animations?.contains(it.animationId) == false }