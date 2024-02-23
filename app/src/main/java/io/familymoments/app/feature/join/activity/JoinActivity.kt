package io.familymoments.app.feature.join.activity

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.base.BaseActivity
import io.familymoments.app.feature.join.screen.JoinScreen
import io.familymoments.app.feature.join.viewmodel.JoinViewModel

@AndroidEntryPoint
class JoinActivity : BaseActivity<JoinViewModel>(JoinViewModel::class) {
    override val screen: @Composable () -> Unit = { JoinScreen(viewModel) }
}
