package io.familymoments.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import io.familymoments.app.ui.screen.FamilySelectScreen
import io.familymoments.app.ui.theme.FamilyMomentsTheme

class FamilySelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                Surface( color = MaterialTheme.colorScheme.background) {
                    FamilySelectScreen()
                }
            }
        }
    }
}
