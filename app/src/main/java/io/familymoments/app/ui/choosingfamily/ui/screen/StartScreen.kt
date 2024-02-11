package io.familymoments.app.ui.choosingfamily.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
        { navController.navigate(ChoosingFamilyRoute.SEARCHING_MEMBER.name) },
        { navController.navigate(ChoosingFamilyRoute.FAMILY_JOIN.name) }
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
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = AppColors.pink3,
            radius = radius.dp.toPx(),
            center = Offset(36.dp.toPx(), (27.dp + radius.dp).toPx())
        )
    }
    Text(
        modifier = Modifier
            .padding(top = 146.dp, start = 43.dp)
            .clickable {
                goToCreating()
            },
        text = stringResource(id = R.string.create_family),
        style = AppTypography.BTN1_36,
        color = AppColors.deepPurple1
    )
}

@Composable
fun JoinButton(goToJoining: () -> Unit = {}) {
    val radius = 275
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = AppColors.purple4,
            radius = radius.dp.toPx(),
            center = Offset(size.width - 50.dp.toPx(), (234.dp + radius.dp).toPx())
        )
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        Text(
            modifier = Modifier
                .padding(bottom = 227.dp, end = 50.dp)
                .clickable {
                    goToJoining()
                },
            text = stringResource(id = R.string.join_family),
            style = AppTypography.BTN1_36,
            color = AppColors.deepPurple1
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
