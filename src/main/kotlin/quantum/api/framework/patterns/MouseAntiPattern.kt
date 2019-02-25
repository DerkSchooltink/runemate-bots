package quantum.api.framework.patterns

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.input.Mouse
import com.runemate.game.api.hybrid.local.Screen
import com.runemate.game.api.hybrid.local.hud.InteractablePoint
import com.runemate.game.api.hybrid.util.calculations.Random

class MouseAntiPattern : PatternType {

    override fun execute(): Boolean {
        val randomX = Random.nextInt(0, Screen.getBounds()?.maxX?.toInt() ?: 0)
        val randomY = Random.nextInt(0, Screen.getBounds()?.maxY?.toInt() ?: 0)
        val point = InteractablePoint(randomX, randomY)
        Environment.getBot().logger.info("Performing AntiPattern {MAP}")
        return Mouse.CLOUSE_PATH_GENERATOR.move(point, 1.0)
    }
}