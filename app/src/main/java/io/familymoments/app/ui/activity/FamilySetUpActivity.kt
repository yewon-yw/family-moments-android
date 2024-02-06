package io.familymoments.app.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import io.familymoments.app.ui.familyselect.ui.screen.FamilySetScreen
import io.familymoments.app.ui.familyselect.viewmodel.FamilySetViewModel
import io.familymoments.app.ui.theme.FamilyMomentsTheme

@AndroidEntryPoint
class FamilySetActivity : BaseActivity<FamilySetViewModel>(FamilySetViewModel::class) {
    override val screen: @Composable () -> Unit = { FamilySetScreen() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyMomentsTheme {
                // A surface container using the 'background' color from the theme
                screen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FamilyMomentsTheme {
        Greeting("Android")
    }
}
