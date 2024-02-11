package io.familymoments.app.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
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
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                screen()
            }
        }
    }
}
