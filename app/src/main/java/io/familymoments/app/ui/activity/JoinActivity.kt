package io.familymoments.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.familymoments.app.ui.screen.JoinScreen
import io.familymoments.app.ui.theme.FamilyMomentsTheme

class JoinActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                JoinScreen()
            }
        }
    }
}