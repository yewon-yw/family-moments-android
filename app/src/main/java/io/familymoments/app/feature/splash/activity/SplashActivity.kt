package io.familymoments.app.feature.splash.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.base.BaseActivity
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.feature.splash.screen.SplashScreen
import io.familymoments.app.feature.splash.viewmodel.SplashViewModel

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>(SplashViewModel::class) {
    override val screen: @Composable () -> Unit = {
        SplashScreen(
            viewModel = viewModel,
            onFinish = { finish() }
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        actionBar?.hide()

        val uri = intent.data
        if (uri != null) {
            viewModel.handleDeepLink(uri)
        }

        setContent {
            FamilyMomentsTheme {
                screen()
            }
        }
    }
}
