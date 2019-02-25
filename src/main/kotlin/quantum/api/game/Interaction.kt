package quantum.api.game

import com.runemate.game.api.hybrid.Environment
import com.runemate.game.api.hybrid.entities.GameObject
import com.runemate.game.api.hybrid.entities.LocatableEntity
import com.runemate.game.api.hybrid.entities.Npc
import com.runemate.game.api.hybrid.entities.details.Interactable
import com.runemate.game.api.hybrid.input.Mouse
import com.runemate.game.api.hybrid.local.Camera
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem
import com.runemate.game.api.hybrid.util.Regex
import com.runemate.game.api.hybrid.util.calculations.Random
import com.runemate.game.api.script.Execution
import quantum.api.game.Interaction.deselectItem
import java.util.regex.Pattern

object Interaction {
    fun deselectItem(): Boolean {
        val item = Inventory.getSelectedItem()
        return item == null || item.click() || Mouse.click(Mouse.Button.LEFT)
    }
}

fun Mouse.Button.fastClick(button: Mouse.Button = Mouse.Button.LEFT): Boolean {
    if (Mouse.press(button)) {
        Execution.delay(Random.nextGaussian(90.0, 180.0).toLong())
    }
    return Mouse.release(button)
}

fun SpriteItem?.selectItem(item: SpriteItem?): Boolean {
    if (item == null) {
        Environment.getLogger().warn("Cannot select null item")
        return false
    }
    val selectedItem = Inventory.getSelectedItem()
    return if (selectedItem != null && selectedItem != item && !deselectItem()) false
    else item.interact("Use") && Execution.delayUntil({ item == Inventory.getSelectedItem() }, 600, 1200)
}

fun Interactable?.interact(action: Pattern?) = this != null && interact(action)
fun Interactable?.interact(action: String) = interact(Regex.getPatternForExactString(action))
fun Interactable?.interact(action: Pattern?, name: Pattern?) = this != null && interact(action, name)
fun Interactable?.interact(action: Pattern?, name: String?) = interact(action, Regex.getPatternForExactString(name))
fun Interactable?.interact(action: String, name: Pattern?) = interact(Regex.getPatternForExactString(action), name)
fun Interactable?.interact(action: String?, name: String?) = interact(Regex.getPatternForExactString(action), Regex.getPatternForExactString(name))

fun LocatableEntity?.turnAndInteract(action: Pattern?, force: Boolean = false): Boolean {
    if (this != null && (force || this.visibility <= 50)) {
        if (Environment.isOSRS()) {
            Camera.concurrentlyTurnTo(this, Random.nextDouble(0.8, 1.0))
        } else {
            Camera.concurrentlyTurnTo(this, Random.nextDouble(0.35, 0.55))
        }
    }
    return interact(action)
}

fun LocatableEntity?.turnAndInteract(action: String?, force: Boolean = false): Boolean {
    return turnAndInteract(Regex.getPatternForExactString(action), force)
}

fun LocatableEntity?.turnAndInteractDynamic(action: Pattern?): Boolean {
    if (this?.isVisible == false) {
        val pitch = Camera.getPitch()
        Camera.concurrentlyTurnTo(this, Random.nextDouble(pitch * 0.75, pitch))
    }
    return interact(action)
}

fun LocatableEntity?.turnAndInteractDynamic(action: String) = turnAndInteractDynamic(Regex.getPatternForExactString(action))

fun SpriteItem?.useOn(gameObject: GameObject?): Boolean {
    if (Inventory.getSelectedItem() != this) interact("Use")
    return gameObject.interact("Use", "${this?.definition?.name} -> ${gameObject?.definition?.name}")
}

fun SpriteItem?.useOn(secondItem: SpriteItem?) =
        interact("Use") && secondItem.interact("Use", "${this?.definition?.name} -> ${secondItem?.definition?.name}")

fun Npc?.twoTick(condition: () -> Boolean) {
    Camera.concurrentlyTurnTo(this)
    while (this?.isValid == true && condition()) {
        click()
        Execution.delay(850)
    }
}