package pt.up.fe.cpm.tiktek.core.ui.transition

import androidx.compose.animation.core.PathEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Path

// M 0,0 C 0.05, 0, 0.133333, 0.06, 0.166666, 0.4 C 0.208333, 0.82, 0.25, 1, 1, 1
private val path =
    Path().apply {
        moveTo(0f, 0f)
        cubicTo(0.05f, 0f, 0.133333f, 0.06f, 0.166666f, 0.4f)
        cubicTo(0.208333f, 0.82f, 0.25f, 1f, 1f, 1f)
    }

private val easing = PathEasing(path)

internal fun <T> spec() =
    tween<T>(
        durationMillis = 300,
        easing = easing,
    )
