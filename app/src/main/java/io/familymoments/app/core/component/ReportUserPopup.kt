package io.familymoments.app.core.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import io.familymoments.app.R
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import kotlin.math.roundToInt

@Composable
fun ReportUserPopup(
    showPopup: Boolean = false,
    onDismissRequest: () -> Unit = {},
    offset: Offset = Offset.Zero
) {
    val popupOffsetPx = Offset(236.dp.toPx() * 0.12f, 7.dp.toPx())
    if (showPopup) {
        Popup(
            onDismissRequest = onDismissRequest,
            offset = IntOffset(
                (offset.x - popupOffsetPx.x).roundToInt(),
                (offset.y + popupOffsetPx.y).roundToInt()
            )
        ) {
            Box(modifier = Modifier.size(236.dp, 145.dp)) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height
                    val triangleHeight = 30f
                    drawRoundRect(
                        color = AppColors.grey9,
                        size = Size(width, height - triangleHeight),
                        cornerRadius = CornerRadius(10f, 10f),
                        topLeft = Offset(0f, triangleHeight)
                    )
                    val trianglePath = Path().apply {
                        moveTo(width * 0.12f, 2f)
                        lineTo(width * 0.09f, triangleHeight + 1)
                        lineTo(width * 0.15f, triangleHeight + 1)
                        close()
                    }
                    drawPath(trianglePath, color = AppColors.grey9)
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(AppColors.grey7)
                        .align(Alignment.BottomCenter)
                ) {
                    items(3) { index ->
                        ReportUserItem()
                        if (index < 2) {
                            HorizontalDivider(color = AppColors.grey6, thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReportUserItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(horizontal = 11.dp, vertical = 7.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_profile_test),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 11.dp)
                .size(24.dp)
                .clip(CircleShape)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 11.dp)
                .align(Alignment.CenterVertically),
            text = "민니",
            style = AppTypography.LB2_11,
            color = AppColors.grey6
        )
        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_report_user),
            tint = AppColors.grey8,
            contentDescription = null
        )
    }
}

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ReportUserPopup(showPopup = true, offset = Offset(0f, 0f))
}
