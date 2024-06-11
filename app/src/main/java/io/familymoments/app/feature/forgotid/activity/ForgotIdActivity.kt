package io.familymoments.app.feature.forgotid.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.feature.forgotid.screen.ForgotIdScreen
import io.familymoments.app.feature.forgotpassword.activity.ForgotPasswordActivity
import io.familymoments.app.feature.login.activity.LoginActivity

@AndroidEntryPoint
class ForgotIdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                    ForgotIdScreen(::goToLogin, ::goToForgotPwd)
            }
        }
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun goToForgotPwd(){
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
