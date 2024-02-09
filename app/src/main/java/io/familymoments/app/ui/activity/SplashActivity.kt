package io.familymoments.app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.screen.SplashScreen
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.SplashViewModel
import kotlinx.coroutines.launch

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
                lifecycleScope.launch {
                    viewModel.splashUiState.collect { splashUiState ->
                        if (splashUiState.isSuccess == true) {
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                            // 메인 화면 이동
                        } else if (splashUiState.isSuccess == false) {
                            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        }
                    }
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
