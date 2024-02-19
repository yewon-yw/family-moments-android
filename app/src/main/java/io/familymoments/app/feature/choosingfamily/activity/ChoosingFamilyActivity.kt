package io.familymoments.app.feature.choosingfamily.activity

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.core.base.BaseActivity
import io.familymoments.app.feature.choosingfamily.screen.ChoosingFamilyScreen
import io.familymoments.app.feature.choosingfamily.viewmodel.ChoosingFamilyViewModel

@AndroidEntryPoint
class ChoosingFamilyActivity : BaseActivity<ChoosingFamilyViewModel>(ChoosingFamilyViewModel::class) {
    override val screen: @Composable () -> Unit = { ChoosingFamilyScreen() }
}
