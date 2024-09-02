package io.familymoments.app.core.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import io.familymoments.app.R
import io.familymoments.app.core.component.popup.DeletePopUp
import io.familymoments.app.core.network.dto.response.Member
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import kotlin.math.roundToInt

@Composable
fun ReportUserPopup(
    showPopup: Boolean = false,
    onDismissRequest: () -> Unit = {},
    members: List<Member> = emptyList(),
    offset: Offset = Offset.Zero,
    reportUser: (String) -> Unit = {}
) {
    val popupOffsetPx = Offset(236.dp.toPx() * 0.12f, 7.dp.toPx())
    val triangleHeight = 30f
    val triangleHeightDp = triangleHeight.toDp()
    val maxHeight = calculateHeight(items = 5, triangleHeightDp) // 최대 5명까지 한 화면에
    var showConfirmPopup by remember { mutableStateOf(false) }
    var reportUserId by remember { mutableStateOf("") }

    if (showPopup && members.isNotEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .clickable { onDismissRequest() }
        )
        Popup(
            onDismissRequest = onDismissRequest,
            offset = IntOffset(
                (offset.x - popupOffsetPx.x).roundToInt(),
                (offset.y + popupOffsetPx.y).roundToInt()
            )
        ) {
            Box(
                modifier = Modifier
                    .heightIn(max = maxHeight)
                    .size(236.dp, calculateHeight(items = members.size, triangleHeightDp))
            ) {
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val width = size.width
                    val height = size.height
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
                        .padding(top = triangleHeightDp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(AppColors.grey7)
                        .align(Alignment.BottomCenter)
                ) {
                    itemsIndexed(
                        items = members,
                        key = { _, member -> member.id }
                    ) { index: Int, member: Member ->
                        ReportUserItem(
                            member = member,
                            updateReportUserId = { reportUserId = it },
                            updatePopupVisibility = { showConfirmPopup = it }
                        )
                        if (index < members.size - 1) {
                            HorizontalDivider(color = AppColors.grey6, thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    }
    ReportUserConfirmPopup(
        showConfirmPopup = showConfirmPopup && reportUserId.isNotEmpty(),
        userId = reportUserId,
        reportUser = reportUser,
        onDismissRequest = { showConfirmPopup = false }
    )
}

@Composable
fun ReportUserItem(
    member: Member,
    updateReportUserId: (String) -> Unit,
    updatePopupVisibility: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .padding(horizontal = 11.dp, vertical = 7.dp)
    ) {
        AsyncImage(
            model = member.profileImg,
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
            text = member.nickname,
            style = AppTypography.LB2_11,
            color = AppColors.grey6
        )
        Icon(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    updateReportUserId(member.id)
                    updatePopupVisibility(true)
                },
            painter = painterResource(id = R.drawable.ic_report_user),
            tint = AppColors.grey8,
            contentDescription = null
        )
    }
}

@Composable
fun ReportUserConfirmPopup(
    showConfirmPopup: Boolean = false,
    userId: String,
    reportUser: (String) -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (showConfirmPopup) {
        DeletePopUp(
            content = stringResource(id = R.string.report_user_popup_content),
            deleteBtnLabel = stringResource(id = R.string.report_user_popup_btn),
            delete = {
                reportUser(userId)
                onDismissRequest()
            },
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@Composable
fun Float.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}

private fun calculateHeight(items: Int, triangleHeight: Dp): Dp = 38.dp * items + 20.dp + triangleHeight

@Preview(showBackground = true, widthDp = 300, heightDp = 400)
@Composable
fun ReportUserPopupPreview() {
    ReportUserPopup(
        showPopup = true,
        offset = Offset(100f, 0f),
        members = listOf(
            Member(id = "a", nickname = "베리"),
            Member(id = "b", nickname = "코비"),
            Member(id = "c", nickname = "토미"),
            Member(id = "d", nickname = "토미"),
        )
    )
}
