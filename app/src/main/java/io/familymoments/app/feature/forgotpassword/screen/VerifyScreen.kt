package io.familymoments.app.feature.forgotpassword.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography

@Composable
fun VerifyScreen(navigate: () -> Unit) {
    val (name, email, certifyNumber) = List(3) { remember { mutableStateOf(TextFieldValue()) } }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .weight(1f, fill = false)
        ) {
            Spacer(modifier = Modifier.height(68.dp))
            Text(text = stringResource(id = R.string.forgot_password_title_01), style = AppTypography.H1_36)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.forgot_password_desc_01),
                style = AppTypography.LB1_13,
                color = AppColors.grey2
            )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.forgot_password_tf_name_title),
                style = AppTypography.SH2_18,
                color = AppColors.grey2
            )
            Spacer(modifier = Modifier.height(8.dp))
            FMTextField(
                value = name.value,
                onValueChange = { name.value = it },
                hint = stringResource(id = R.string.forgot_password_tf_name_hint),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.forgot_password_tf_email_title),
                style = AppTypography.SH2_18,
                color = AppColors.grey2
            )
            Row {
                FMTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    hint = stringResource(id = R.string.forgot_password_tf_email_hint),
                    modifier = Modifier
                        .weight(1f),
                )
                FMButton(
                    onClick = {},
                    text = stringResource(id = R.string.forgot_password_btn_cert),
                    modifier = Modifier.padding(start = 8.dp),
                    radius = 10.dp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            FMTextField(
                value = certifyNumber.value,
                onValueChange = { certifyNumber.value = it },
                hint = stringResource(id = R.string.forgot_password_tf_cn_hint),
            )

            Spacer(modifier = Modifier.weight(1f))
            FMButton(
                onClick = navigate,
                text = stringResource(id = R.string.next),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
