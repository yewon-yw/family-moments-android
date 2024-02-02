package io.familymoments.app.ui.screen

import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.FamilyMomentsTheme


@Composable
fun FamilySelectScreen(modifier: Modifier = Modifier) {
    CreateFamily()
    JoinFamily()
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 95.dp), verticalAlignment = Alignment.Bottom
    ) {
        SkipButton()

    }
}

@Composable
fun CreateFamily() {
    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()
    val style = TextStyle(
        fontSize = 36.sp,
        color = AppColors.deepPurple1,
        fontWeight = FontWeight(700)
    )
    val radius = 275
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = AppColors.pink3,
            radius = radius.dp.toPx(),
            center = Offset(36.dp.toPx(), (27.dp + radius.dp).toPx())
        )
        drawText(
            textMeasurer = textMeasurer,
            text = context.getString(R.string.create_family),
            style = style,
            topLeft = Offset(43.dp.toPx(), 146.dp.toPx())
        )
    }
}

@Composable
fun JoinFamily() {
    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()
    val style = TextStyle(
        fontSize = 36.sp,
        color = AppColors.deepPurple1,
        fontWeight = FontWeight(700)
    )
    val radius = 275
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = AppColors.purple4,
            radius = radius.dp.toPx(),
            center = Offset(size.width - 50.dp.toPx(), (234.dp + radius.dp).toPx())
        )
        drawText(
            textMeasurer = textMeasurer,
            text = context.getString(R.string.join_family),
            style = style,
            topLeft = Offset(169.dp.toPx(), 357.dp.toPx())
        )
    }
}

@Composable
fun SkipButton() {
    Button(
        onClick = { /*TODO*/ },
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
fun FamilySelectScreenPreview() {
    FamilyMomentsTheme {
        FamilySelectScreen()
    }
}
