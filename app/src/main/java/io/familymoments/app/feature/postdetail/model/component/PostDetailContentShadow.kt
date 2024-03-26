package io.familymoments.app.feature.postdetail.model.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.familymoments.app.core.theme.AppColors

fun Modifier.postDetailContentShadow(
    color: Color = AppColors.shadowColor,
    alpha: Float = 0.2f,
    shadowRadius: Dp = 10.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
) = this.drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            left = -2.dp.toPx(),
            top = 4.dp.toPx(),
            right = this.size.width + 2.dp.toPx(),
            bottom = this.size.height + 8.dp.toPx(),
            radiusX = 0f,
            radiusY = 0f,
            paint = paint
        )
    }
}
