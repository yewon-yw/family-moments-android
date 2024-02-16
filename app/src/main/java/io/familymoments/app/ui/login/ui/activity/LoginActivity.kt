package io.familymoments.app.ui.login.ui.activity

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.activity.BaseActivity
import io.familymoments.app.ui.login.ui.screen.LoginScreen
import io.familymoments.app.ui.login.viewmodel.LoginViewModel

@AndroidEntryPoint

class LoginActivity : BaseActivity<LoginViewModel>(LoginViewModel::class) {
    override val screen:   @Composable () -> Unit = { LoginScreen(viewModel) }
}
