package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import io.familymoments.app.R
import io.familymoments.app.ui.theme.FamilyMomentsTheme

@Composable
fun SplashScreen() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_splash_title),
            contentDescription = "splash title"
        )
        Text(text = "가족들과 소중한 순간을 공유해보세요!", color = Color(0xFFA9A9A9))
        Image(
            painter = painterResource(id = R.drawable.ic_splash_icon),
            contentDescription = "splash icon"
        )
    }
}

@Preview
@Composable
fun SplashPreview() {
    FamilyMomentsTheme {
        SplashScreen()
    }
}