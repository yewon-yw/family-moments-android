package io.familymoments.app.ui.choosingfamily.ui.activity

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.activity.BaseActivity
import io.familymoments.app.ui.choosingfamily.ui.screen.ChoosingFamily
import io.familymoments.app.ui.choosingfamily.viewmodel.ChoosingFamilyViewModel

@AndroidEntryPoint
class FamilySetActivity : BaseActivity<ChoosingFamilyViewModel>(ChoosingFamilyViewModel::class) {
    override val screen: @Composable () -> Unit = { ChoosingFamily() }
}
