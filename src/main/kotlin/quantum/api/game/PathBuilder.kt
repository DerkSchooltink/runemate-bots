package quantum.api.game

import com.runemate.game.api.hybrid.entities.details.Locatable
import com.runemate.game.api.hybrid.location.navigation.Landmark
import com.runemate.game.api.hybrid.location.navigation.Path
import com.runemate.game.api.hybrid.location.navigation.Traversal
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath

object PathBuilder {
    fun buildTo(locatable: Locatable?): Path? {
        if (locatable == null) return null
        val webPath = Traversal.getDefaultWeb().pathBuilder.buildTo(locatable)
        return if (webPath != null && webPath.next != null) webPath else RegionPath.buildTo(locatable)
                ?: BresenhamPath.buildTo(locatable)
    }

    fun buildTo(landmark: Landmark?): Path? {
        if (landmark == null) return null
        return Traversal.getDefaultWeb().pathBuilder.buildTo(landmark)
    }
}