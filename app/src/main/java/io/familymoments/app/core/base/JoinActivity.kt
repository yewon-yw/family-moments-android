package io.familymoments.app.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.screen.JoinScreen
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.JoinViewModel

@AndroidEntryPoint
class JoinActivity : BaseActivity<JoinViewModel>(JoinViewModel::class) {
    override val screen: @Composable () -> Unit = { JoinScreen(viewModel) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme(content = screen)
        }
    }
}
