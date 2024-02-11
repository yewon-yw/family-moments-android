package io.familymoments.app.ui.choosingfamily.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.familymoments.app.R
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography
import io.familymoments.app.ui.theme.FamilyMomentsTheme


@Composable
fun StartScreen(navController: NavController) {
    StartScreen(
        { navController.navigate(ChoosingFamilyRoute.SEARCH_MEMBER.name) },
        { navController.navigate(ChoosingFamilyRoute.JOIN.name) }
    )
}

@Composable
fun StartScreen(goToCreating: () -> Unit = {}, goToJoining: () -> Unit = {}) {

    CreateButton(goToCreating)
    JoinButton(goToJoining)
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 95.dp), verticalAlignment = Alignment.Bottom
    ) {
        SkipButton()

    }
}


@Composable
fun CreateButton(goToCreating: () -> Unit = {}) {
    val radius = 275
    DrawCreateCircle(radius, goToCreating)
    Text(
        modifier = Modifier
            .padding(top = 146.dp, start = 43.dp),
        text = stringResource(id = R.string.create_family),
        style = AppTypography.BTN1_36,
        color = AppColors.deepPurple1
    )
}

fun calculateCreateCircleCenter(radius: Int): Pair<Int, Int> {
    return (radius - 226 to 27 + radius)
}

@Composable
fun DrawCreateCircle(radius: Int, goToCreating: () -> Unit) {
    val center = calculateCreateCircleCenter(radius)
    val minX = (center.first - radius).dp
    val maxX = (center.first + radius).dp
    val minY = (center.second - radius).dp
    val maxY = (center.second + radius).dp

    var color by remember {
        mutableStateOf(AppColors.pink3)
    }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(goToCreating) {
            awaitEachGesture {
                awaitFirstDown().also {
                    val positionX = it.position.x
                    val positionY = it.position.y
                    if ((positionX in minX.toPx()..maxX.toPx()) && (positionY in minY.toPx()..maxY.toPx())) {
                        color = AppColors.pink2
                        it.consume()
                    }
                }
                val up = waitForUpOrCancellation()
                if (up != null) {
                    color = AppColors.pink3
                    up.consume()
//                    goToCreating()

                }
            }
        }) {
        drawCircle(
            color = color,
            radius = radius.dp.toPx(),
            center = Offset(center.first.dp.toPx(), center.second.dp.toPx())
        )
    }
}

@Composable
fun JoinButton(goToJoining: () -> Unit = {}) {
    val radius = 275
    DrawJoinCircle(radius = radius, goToJoining)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Text(
            modifier = Modifier
                .padding(bottom = 227.dp, end = 50.dp),
            text = stringResource(id = R.string.join_family),
            style = AppTypography.BTN1_36,
            color = AppColors.deepPurple1
        )
    }
}

fun calculateJoinCircleCenter(radius: Int): Pair<Int, Int> {
    return (50 + radius to 234 + radius)
}

@Composable
fun DrawJoinCircle(radius: Int, goToJoining: () -> Unit) {
    val center = calculateJoinCircleCenter(radius)
    val minX = (center.first - radius).dp
    val maxX = (center.first + radius).dp
    val minY = (center.second - radius).dp
    val maxY = (center.second + radius).dp

    var color by remember {
        mutableStateOf(AppColors.purple4)
    }
    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(goToJoining) {
            awaitEachGesture {
                awaitFirstDown().also {
                    val positionX = it.position.x
                    val positionY = it.position.y
                    if ((positionX in minX.toPx()..maxX.toPx()) && (positionY in minY.toPx()..maxY.toPx())) {
                        color = AppColors.purple3
                        it.consume()
                    }
                }
                val up = waitForUpOrCancellation()
                if (up != null) {
                    color = AppColors.purple4
                    up.consume()
//                    goToCreating()

                }
            }
        }) {
        drawCircle(
            color = color,
            radius = radius.dp.toPx(),
            center = Offset(center.first.dp.toPx(), center.second.dp.toPx())
        )
    }
}

@Composable
fun SkipButton(onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(60.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.deepPurple1,
            contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(R.string.skip_select_family_option),
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 18.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartScreen() {
    FamilyMomentsTheme {
        StartScreen()
    }
}
