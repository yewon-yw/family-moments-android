package io.familymoments.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.R
import io.familymoments.app.ui.component.AppBarScreen
import io.familymoments.app.ui.component.FMButton
import io.familymoments.app.ui.component.FMTextField
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.AppTypography
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.ForgotPasswordViewModel

@ExperimentalMaterial3Api
@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IdentifyPreview() {
    FamilyMomentsTheme {
        AppBarScreen(
            title = {
                Text(
                    text = stringResource(id = R.string.forgot_password_title_01),
                    style = AppTypography.SH3_16
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { },
                    content = { Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null) })
            },
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(68.dp))
                Text(text = stringResource(id = R.string.forgot_password_title_01), style = AppTypography.H1_36)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password_desc_01),
                    style = AppTypography.LB1_13,
                    color = AppColors.grey2
                )
                Spacer(modifier = Modifier.height(32.dp))

                FMTextField(
                    value = TextFieldValue(),
                    onValueChange = {},
                    hint = stringResource(id = R.string.forgot_password_tf_id_hint),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
                FMButton(onClick = {}, text = "다음", modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyPreview() {
    FamilyMomentsTheme {
        AppBarScreen(
            title = {
                Text(
                    text = stringResource(id = R.string.forgot_password_title_01),
                    style = AppTypography.SH3_16
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { },
                    content = { Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null) })
            },
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
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
                    value = TextFieldValue(),
                    onValueChange = {},
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
                        value = TextFieldValue(),
                        onValueChange = {},
                        hint = stringResource(id = R.string.forgot_password_tf_email_hint),
                        modifier = Modifier
                            .weight(1f),
                    )
                    FMButton(
                        onClick = { },
                        text = stringResource(id = R.string.forgot_password_btn_cert),
                        modifier = Modifier.padding(start = 8.dp),
                        radius = 10.dp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                FMTextField(
                    value = TextFieldValue(),
                    onValueChange = {},
                    hint = stringResource(id = R.string.forgot_password_tf_cn_hint),
                )

                Spacer(modifier = Modifier.weight(1f))
                FMButton(onClick = {}, text = "다음", modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResetPreview() {
    FamilyMomentsTheme {
        AppBarScreen(
            title = {
                Text(
                    text = stringResource(id = R.string.forgot_password_title_01),
                    style = AppTypography.SH3_16
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { },
                    content = { Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = null) })
            },
        ) {
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
                    value = TextFieldValue(),
                    onValueChange = {},
                    hint = stringResource(id = R.string.forgot_password_tf_pw_hint),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                FMTextField(
                    value = TextFieldValue(),
                    onValueChange = {},
                    hint = stringResource(id = R.string.forgot_password_tf_pw_confirm_hint),
                )

                Spacer(modifier = Modifier.weight(1f))
                FMButton(
                    onClick = {},
                    text = stringResource(id = R.string.forgot_password_btn_reset),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
