package io.familymoments.app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.familymoments.app.ui.screen.SplashScreen

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                // 토큰 확인해서 유효하면 메인 화면으로 이동
                // TODO 메인 화면 이동

                // 유효하지 않으면 로그인 화면 연결
                startActivity(Intent(this, LoginActivity::class.java))
            },
            500L,
        )
        setContent {
            SplashScreen()
        }
    }
}
