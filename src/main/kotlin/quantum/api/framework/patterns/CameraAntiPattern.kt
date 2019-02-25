package quantum.api.framework.patterns

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.input.Mouse
import com.runemate.game.api.hybrid.local.Screen
import com.runemate.game.api.hybrid.local.hud.InteractablePoint
import com.runemate.game.api.hybrid.util.calculations.Random

class CameraAntiPattern : PatternType {

    override fun execute(): Boolean {
        val x = Random.nextInt(0, Screen.getBounds()?.maxX?.toInt() ?: 0)
        val y = Random.nextInt(0, Screen.getBounds()?.maxY?.toInt() ?: 0)
        val point = InteractablePoint(x, y)

        if (Mouse.press(Mouse.Button.WHEEL)) Mouse.CLOUSE_PATH_GENERATOR.move(point, Random.nextDouble(0.75, 1.25))
        Environment.getBot().logger.info("Performing AntiPattern {CAP}")
        return Mouse.release(Mouse.Button.WHEEL)
    }
}