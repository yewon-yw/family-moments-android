package io.familymoments.app.feature.forgotpassword.screen

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.familymoments.app.R
import io.familymoments.app.core.component.AppBarScreen
import io.familymoments.app.core.component.FMButton
import io.familymoments.app.core.component.FMTextField
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.feature.forgotpassword.viewmodel.ForgotPasswordViewModel
import io.familymoments.app.feature.forgotpassword.graph.ForgotPasswordRoute
import io.familymoments.app.feature.forgotpassword.graph.forgotPasswordGraph

@ExperimentalMaterial3Api
@Composable
fun ForgotPasswordScreen(viewModel: ForgotPasswordViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.forgot_password_title_01),
                style = AppTypography.SH3_16,
                color = AppColors.deepPurple1
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    (context as Activity).finish()
                },
                painter = painterResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = Color.Unspecified
            )
        },
    ) {
        NavHost(navController = navController, startDestination = ForgotPasswordRoute.IDENTIFY.name) {
            forgotPasswordGraph(navController)
        }
    }
}

@Composable
fun IdentifyScreen(navigate: () -> Unit) {
    var identify by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(68.dp))
        Text(
            text = stringResource(id = R.string.forgot_password_title_01),
            style = AppTypography.H1_36,
            color = AppColors.deepPurple1
        )
        Spacer(modifier = Modifier.height(13.dp))
        Text(
            text = stringResource(id = R.string.forgot_password_desc_01),
            style = AppTypography.LB1_13,
            color = AppColors.grey2
        )
        Spacer(modifier = Modifier.height(61.dp))

        FMTextField(
            value = identify,
            onValueChange = { identify = it },
            hint = stringResource(id = R.string.forgot_password_tf_id_hint),
            modifier = Modifier.width(240.dp).align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(1f))
        FMButton(
            onClick = navigate,
            text = stringResource(id = R.string.next),
            modifier = Modifier.fillMaxWidth(),
            contentPaddingValues =  PaddingValues(top = 20.dp, bottom = 18.dp),
            contentModifier = Modifier
        )
        Spacer(modifier = Modifier.height(101.dp))
    }
}

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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IdentifyPreview() {
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.forgot_password_title_01),
                style = AppTypography.SH3_16
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    //todo 뒤로가기
                },
                painter = painterResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = Color.Unspecified
            )
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
            FMButton(onClick = {}, text = stringResource(id = R.string.next), modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyPreview() {
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.forgot_password_title_01),
                style = AppTypography.SH3_16
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                   //todo 뒤로가기
                },
                painter = painterResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = Color.Unspecified
            )
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResetPreview() {
    AppBarScreen(
        title = {
            Text(
                text = stringResource(id = R.string.forgot_password_title_01),
                style = AppTypography.SH3_16
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable {
                    //todo 뒤로가기
                },
                painter = painterResource(id = R.drawable.ic_app_bar_back),
                contentDescription = null,
                tint = Color.Unspecified
            )
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
                modifier = Modifier.background(color = AppColors.pink5)
            )
            Spacer(modifier = Modifier.height(16.dp))
            FMTextField(
                value = TextFieldValue(),
                onValueChange = {},
                hint = stringResource(id = R.string.forgot_password_tf_pw_confirm_hint),
                modifier = Modifier.background(color = AppColors.pink5)
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
