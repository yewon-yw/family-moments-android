package io.familymoments.app.core.util

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    this.clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


inline fun Modifier.oneClick(crossinline onClick: () -> Unit): Modifier = composed {
    val buttonState = remember { mutableStateOf(true) }
    this.clickable {
        if (buttonState.value) {
            buttonState.value = false
            onClick()
        }
    }
}

inline fun Modifier.oneClick(delay: Long, crossinline onClick: () -> Unit): Modifier = composed {
    val buttonState = remember { mutableStateOf(true) }
    this.clickable {
        if (buttonState.value) {
            buttonState.value = false
            onClick()
            Handler(Looper.getMainLooper()).postDelayed({
                buttonState.value = true
            }, delay)
        }
    }
}

inline fun Modifier.removeDropdownPadding(vertical: Dp = 8.dp): Modifier = composed {
    this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(
            placeable.width,
            placeable.height - (vertical * 2).toPx().toInt()
        ) {
            placeable.placeRelative(0, -vertical.toPx().toInt())
        }
    }
}
