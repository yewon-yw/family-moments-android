package io.familymoments.app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.screen.SplashScreen
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override val screen: @Composable () -> Unit = { SplashScreen(viewModel) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                viewModel.checkUserValidation()
                val splashUiState = viewModel.splashUiState.value

                if (splashUiState.isSuccess == true) {
                    // 메인 화면 이동
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            },
            1000L,
        )

        setContent {
            FamilyMomentsTheme {
                screen()
            }
        }
    }
}
