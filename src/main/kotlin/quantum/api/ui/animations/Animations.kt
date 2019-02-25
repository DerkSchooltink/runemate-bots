package quantum.api.ui.animations

import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.scene.Node
import javafx.scene.effect.MotionBlur
import javafx.util.Duration


object Animations {

    fun fadeIn(node: Node, duration: Double, delay: Double): Timeline {
        node.opacity = 0.0
        val kvo = KeyValue(node.opacityProperty(), 1, Interpolator.EASE_BOTH)
        val kf = KeyFrame(Duration.millis(duration), kvo)

        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)
        return timeline
    }

    fun fadeOut(node: Node, duration: Double, delay: Double): Timeline {
        node.opacity = 1.0
        val kvo = KeyValue(node.opacityProperty(), 0, Interpolator.EASE_BOTH)
        val kf = KeyFrame(Duration.millis(duration), kvo)

        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)
        return timeline
    }

    fun fadeAndTranslateX(node: Node, opacity: Double, start: Double, end: Double, duration: Double, delay: Double): Timeline {
        val blur = MotionBlur(0.0, 10.0)

        node.effect = blur
        node.translateX = start

        val kvr = KeyValue(blur.radiusProperty(), 1, Interpolator.EASE_BOTH)
        val kvo = KeyValue(node.opacityProperty(), opacity, Interpolator.EASE_BOTH)
        val kvx = KeyValue(node.translateXProperty(), end, Interpolator.EASE_BOTH)

        val kf = KeyFrame(Duration.millis(duration), kvr, kvo, kvx)
        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)

        return timeline
    }

    fun fadeAndTranslateY(node: Node, opacity: Double, start: Double, end: Double, duration: Double, delay: Double): Timeline {
        val blur = MotionBlur(0.0, 10.0)

        node.effect = blur
        node.translateY = start
        node.opacity = 0.0

        val kvr = KeyValue(blur.radiusProperty(), 1, Interpolator.EASE_BOTH)
        val kvo = KeyValue(node.opacityProperty(), opacity, Interpolator.EASE_BOTH)
        val kvy = KeyValue(node.translateYProperty(), end, Interpolator.EASE_BOTH)

        val kf = KeyFrame(Duration.millis(duration), kvr, kvo, kvy)
        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)

        return timeline
    }

    fun fadeInAndScale(node: Node, duration: Double, delay: Double): Timeline {
        node.scaleX = 0.0
        node.scaleY = 0.0
        node.opacity = 0.0

        val kvx = KeyValue(node.scaleXProperty(), 1, Interpolator.EASE_BOTH)
        val kvy = KeyValue(node.scaleYProperty(), 1, Interpolator.EASE_BOTH)
        val kvo = KeyValue(node.opacityProperty(), 1, Interpolator.EASE_BOTH)

        val kf = KeyFrame(Duration(duration), kvx, kvy, kvo)
        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)

        return timeline
    }

    fun fadeOutAndScale(node: Node, duration: Double, delay: Double): Timeline {
        node.scaleX = 1.0
        node.scaleY = 1.0
        node.opacity = 1.0

        val kvx = KeyValue(node.scaleXProperty(), 0, Interpolator.EASE_BOTH)
        val kvy = KeyValue(node.scaleYProperty(), 0, Interpolator.EASE_BOTH)
        val kvo = KeyValue(node.opacityProperty(), 0, Interpolator.EASE_BOTH)

        val kf = KeyFrame(Duration(duration), kvx, kvy, kvo)
        val timeline = Timeline(kf)
        timeline.delay = Duration.millis(delay)

        return timeline
    }

    fun scale(node: Node, targetScale: Double, duration: Double, delay: Double): Timeline {
        val blur = MotionBlur(0.0, 3.0)
        node.effect = blur

        val kvr = KeyValue(blur.radiusProperty(), 1, Interpolator.EASE_BOTH)
        val kvx = KeyValue(node.scaleXProperty(), targetScale, Interpolator.EASE_BOTH)
        val kvy = KeyValue(node.scaleYProperty(), targetScale, Interpolator.EASE_BOTH)

        val kf = KeyFrame(Duration(duration), kvr, kvx, kvy)
        val timeline = Timeline(kf)
        timeline.delay = Duration(delay)

        return timeline
    }
}