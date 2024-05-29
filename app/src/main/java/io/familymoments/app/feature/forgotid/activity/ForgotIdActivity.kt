package io.familymoments.app.feature.forgotid.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.feature.forgotid.screen.ForgotIdScreen

@AndroidEntryPoint
class ForgotIdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                    ForgotIdScreen()
            }
        }
    }
}
