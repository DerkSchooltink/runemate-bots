package quantum.api.framework.extensions

import quantum.api.ui.animations.Animations
import javafx.scene.Cursor
import javafx.scene.layout.StackPane

fun StackPane.highlight(pane: StackPane, overrideStyle: Boolean = true) {
    if (overrideStyle) pane.style = "-fx-background-color: #323232"
    cursor = Cursor.HAND
    Animations.scale(pane, 1.05, 125.0, 0.0).play()
}

fun StackPane.defaultStyle(pane: StackPane, overrideStyle: Boolean = true) {
    if (overrideStyle) pane.style = "-fx-background-color: #202020"
    cursor = Cursor.DEFAULT
    Animations.scale(pane, 1.0, 125.0, 0.0).play()
}