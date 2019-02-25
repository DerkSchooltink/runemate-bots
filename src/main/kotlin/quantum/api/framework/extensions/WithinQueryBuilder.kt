package quantum.api.framework.extensions

import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.queries.LocatableEntityQueryBuilder
import com.runemate.game.api.hybrid.queries.QueryBuilder
import com.runemate.game.api.hybrid.region.Players

fun <T : LocatableEntity, QB : QueryBuilder<T, QB, *>> LocatableEntityQueryBuilder<T, QB>.withinRadius(radius: Number): QB =
        within(Players.getLocal()?.position?.let { circularAreaOf(it, radius.toDouble()) })