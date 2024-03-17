package io.familymoments.app.feature.bottomnav.activity

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.base.BaseActivity
import io.familymoments.app.core.network.AuthErrorManager
import io.familymoments.app.feature.bottomnav.screen.MainScreen
import io.familymoments.app.feature.bottomnav.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>(MainViewModel::class) {

    @Inject
    lateinit var authErrorManager: AuthErrorManager

    override val screen: @Composable () -> Unit = { MainScreen(viewModel, authErrorManager) }
}
