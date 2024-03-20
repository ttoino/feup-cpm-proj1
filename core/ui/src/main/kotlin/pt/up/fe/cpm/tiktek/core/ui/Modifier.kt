package pt.up.fe.cpm.tiktek.core.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout

fun Modifier.relativeOffset(
    x: Float = 0f,
    y: Float = 0f
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    layout(placeable.width, placeable.height) {
        placeable.placeRelative((placeable.width * x).toInt(), (placeable.height * y).toInt())
    }
}
