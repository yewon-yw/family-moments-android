package io.familymoments.app.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.screen.ForgotPasswordScreen
import io.familymoments.app.viewmodel.ForgotPasswordViewModel

@AndroidEntryPoint
class ForgotPasswordActivity : BaseActivity<ForgotPasswordViewModel>(ForgotPasswordViewModel::class) {
    @OptIn(ExperimentalMaterial3Api::class)
    override val screen: @Composable () -> Unit = { ForgotPasswordScreen(viewModel) }
}
