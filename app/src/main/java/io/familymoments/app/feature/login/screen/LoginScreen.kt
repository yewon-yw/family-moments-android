package io.familymoments.app.feature.login.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.familymoments.app.R
import io.familymoments.app.feature.bottomnav.activity.MainActivity
import io.familymoments.app.core.component.AppBarScreen
import io.familymoments.app.feature.login.viewmodel.LoginViewModel
import io.familymoments.app.core.theme.AppColors
import io.familymoments.app.core.theme.AppTypography
import io.familymoments.app.core.theme.FamilyMomentsTheme
import io.familymoments.app.feature.join.activity.JoinActivity
import io.familymoments.app.feature.login.model.uistate.LoginUiState

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val loginUiState = viewModel.loginUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val goToJoin = {
        context.startActivity(Intent(context, JoinActivity::class.java))
    }

    if (loginUiState.value.isSuccess == true) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    AppBarScreen(title = {
        Text(
            text = stringResource(R.string.login_app_bar_screen_header),
            style = AppTypography.SH3_16,
            color = AppColors.deepPurple1
        )
    }) {
        LoginScreen(login = viewModel::loginUser, loginUiState.value, goToJoin)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    login: (String, String) -> Unit,
    loginUiState: LoginUiState,
    goToJoin: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxHeight()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginLogo()
        LoginForm(login = login, loginUiState)
        LoginOption(goToJoin)
        SocialLogin()
    }
}

@Composable
fun LoginLogo() {
    Row(modifier = Modifier.padding(top = 86.dp, bottom = 49.dp)) {
        Icon(
            modifier = Modifier.size(110.dp),
            imageVector = ImageVector.vectorResource(R.drawable.ic_splash_icon),
            tint = Color.Unspecified,
            contentDescription = null,
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = stringResource(id = R.string.login_description_01),
                fontSize = 24.sp,
                color = AppColors.deepPurple1,
                style = AppTypography.H2_24,
            )
            Text(
                modifier = Modifier.padding(top = 13.dp),
                text = stringResource(id = R.string.login_description_02),
                fontSize = 13.sp,
                color = AppColors.grey2,
                style = AppTypography.LB1_13
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun LoginForm(
    login: (String, String) -> Unit,
    loginUiState: LoginUiState
) {
    var id by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        LoginFormRoundedCornerTextField(
            label = stringResource(R.string.login_id_text_field_hint),
            onValueChanged = { id = it })
        Spacer(modifier = Modifier.height(8.dp))
        LoginFormRoundedCornerTextField(
            label = stringResource(R.string.login_password_text_field_hint),
            onValueChanged = { password = it })
        if (loginUiState.isSuccess == false) {
            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = loginUiState.errorMessage ?: stringResource(R.string.login_default_error_message),
                style = AppTypography.BTN6_13,
                color = AppColors.red2,
            )
        } else {
            Box(modifier = Modifier.height(37.dp))
        }
        Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    login(id.text, password.text)
                },
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = AppColors.deepPurple1,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_login),
                    fontSize = 18.sp,
                    style = AppTypography.BTN4_18
                )
            }
        }
    }
}

@Composable
fun LoginFormRoundedCornerTextField(
    modifier: Modifier = Modifier,
    label: String,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    var value by remember { mutableStateOf(TextFieldValue()) }
    Surface(shape = RoundedCornerShape(8.dp), modifier = modifier.then(Modifier.fillMaxWidth())) {
        TextField(
            value = value,
            onValueChange = {
                value = it
                onValueChanged(value)
            },
            label = { Text(label, color = AppColors.grey3, style = AppTypography.B1_16) },
            colors =
            TextFieldDefaults.colors(
                focusedContainerColor = AppColors.pink4,
                unfocusedContainerColor = AppColors.pink4,
                disabledContainerColor = AppColors.pink4,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
        )
    }
}

@Composable
fun LoginOption(goToJoin: () -> Unit) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .padding(top = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_forgot_id),
            fontSize = 13.sp,
            color = AppColors.grey2,
            style = AppTypography.BTN6_13
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            color = AppColors.grey2,
            modifier =
            Modifier
                .fillMaxHeight()
                .width(1.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.login_forgot_pw),
            fontSize = 13.sp,
            color = AppColors.grey2,
            style = AppTypography.BTN6_13
        )
        Spacer(modifier = Modifier.width(8.dp))
        Divider(
            color = AppColors.grey2,
            modifier =
            Modifier
                .fillMaxHeight()
                .width(1.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.clickable {
                goToJoin()
            },
            text = stringResource(id = R.string.login_signup),
            fontSize = 13.sp,
            color = AppColors.grey2,
            style = AppTypography.BTN6_13
        )
    }
}

@Composable
fun SocialLogin() {
    Row(
        modifier = Modifier.padding(top = 23.dp, start = 17.dp, end = 17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 4.dp),
            text = stringResource(R.string.sns_login),
            color = AppColors.grey2,
            fontSize = 13.sp,
            style = AppTypography.BTN6_13
        )
        Divider(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
        )
    }

    Spacer(modifier = Modifier.height(30.dp))
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(bottom = 61.dp)
    ) {
        Image(painter = painterResource(id = R.drawable.ic_kakao_login), contentDescription = null)
        Spacer(modifier = Modifier.width(37.dp))
        Image(painter = painterResource(id = R.drawable.ic_naver_login), contentDescription = null)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    FamilyMomentsTheme {
        LoginScreen(login = { _, _ -> }, loginUiState = LoginUiState(), goToJoin = {})
    }
}
