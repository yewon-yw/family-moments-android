package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.ui.component.LoadingIndicator
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.SplashViewModel

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    val splashUiState = viewModel.splashUiState.collectAsState()

    SplashScreen(isLoading = splashUiState.value.isLoading)
}

@Composable
fun SplashScreen(isLoading: Boolean?) {
    if (isLoading == true) {
        LoadingIndicator(isLoading = true)
    } else {
        LoadingIndicator(isLoading = false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.padding(horizontal = 22.dp),
            painter = painterResource(id = R.drawable.splash_title),
            contentDescription = "앱 이름",
        )
        Text(
            modifier = Modifier.padding(top = 9.dp),
            text = "가족들과 소중한 순간을 공유해보세요!",
            color = Color(0xFFA9A9A9),
        )
        Image(
            modifier = Modifier.padding(top = 38.dp),
            painter = painterResource(id = R.drawable.ic_splash_icon),
            contentDescription = "앱 아이콘",
        )
    }
}

@Preview
@Composable
fun SplashPreview() {
    FamilyMomentsTheme {
        SplashScreen(true)
    }
}
