package io.familymoments.app.feature.forgotpassword.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun ResetScreen(navigate: () -> Unit) {
    val context = LocalContext.current
    val (password, passwordConfirm) = List(2) { remember { mutableStateOf(TextFieldValue()) } }
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(68.dp))
        Text(text = stringResource(id = R.string.forgot_password_title_02), style = AppTypography.H1_36)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.forgot_password_desc_02),
            style = AppTypography.LB1_13,
            color = AppColors.grey2
        )

        Spacer(modifier = Modifier.height(8.dp))
        FMTextField(
            value = password.value,
            onValueChange = { password.value = it },
            hint = stringResource(id = R.string.forgot_password_tf_pw_hint),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.pink5)
        )
        Spacer(modifier = Modifier.height(16.dp))
        FMTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = AppColors.pink5),
            value = passwordConfirm.value,
            onValueChange = { passwordConfirm.value = it },
            hint = stringResource(id = R.string.forgot_password_tf_pw_confirm_hint),
        )

        Spacer(modifier = Modifier.weight(1f))
        FMButton(
            onClick = { (context as Activity).finish() },
            text = stringResource(id = R.string.forgot_password_btn_reset),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
