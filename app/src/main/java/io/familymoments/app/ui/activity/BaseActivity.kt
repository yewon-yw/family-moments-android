package io.familymoments.app.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import io.familymoments.app.viewmodel.BaseViewModel
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel>(
    private val vmClass: KClass<VM>
) : ComponentActivity() {

    abstract val screen: @Composable () -> Unit

    protected val viewModel by lazy {

        ViewModelProvider(this, defaultViewModelProviderFactory)[vmClass.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { screen() }
    }
}