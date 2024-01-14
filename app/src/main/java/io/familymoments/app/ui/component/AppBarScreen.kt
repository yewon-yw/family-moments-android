package io.familymoments.app.ui.component

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppBarScreen(
        title: @Composable () -> Unit,
        navigationIcon: @Composable (() -> Unit)? = null,
        content: @Composable () -> Unit,
) {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = title,
                        navigationIcon = navigationIcon,
                        backgroundColor = Color.White,
                        elevation = 5.dp
                )
            }
    ) {
        content()
    }
}