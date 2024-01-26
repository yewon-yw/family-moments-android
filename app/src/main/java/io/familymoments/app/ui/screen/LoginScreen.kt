package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.model.LoginUiState
import io.familymoments.app.ui.theme.AppColors
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.LoginViewModel

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val loginUiState = viewModel.loginUiState.collectAsState()
    LoginScreen(login = viewModel::loginUser, loginUiState = loginUiState.value )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    login:(String, String) -> Unit,
    loginUiState:LoginUiState
){
    Column(modifier = Modifier.background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(86.dp))
        LoginLogo()
        Spacer(modifier = Modifier.height(49.dp))
        LoginForm(login = login, loginUiState = loginUiState)
        LoginOption()
        Spacer(modifier = Modifier.height(15.dp))
        SocialLogin()
    }
}

@Composable
fun LoginLogo() {
    Row {
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
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = stringResource(id = R.string.login_description_02),
                fontSize = 13.sp,
                color = AppColors.grey2,
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun LoginForm(
    login: (String, String) -> Unit,
    loginUiState: LoginUiState,
) {
    var id by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        LoginFormRoundedCornerTextField(label = "Id", onValueChanged = { id = it })
        Spacer(modifier = Modifier.height(8.dp))
        LoginFormRoundedCornerTextField(label = "Password", onValueChanged = { password = it })
        ErrorText(loginUiState)
        Spacer(modifier = Modifier.height(37.dp))
        Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier =
                    Modifier
                        .padding(vertical = 18.dp),
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
            ) {
                Text(
                    text = stringResource(id = R.string.login_login),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 20.dp),
                )
            }
        }
    }
}

@Composable
fun LoginFormRoundedCornerTextField(
    label: String,
    onValueChanged: (TextFieldValue) -> Unit,
) {
    var value by remember { mutableStateOf(TextFieldValue()) }
    Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = {
                value = it
                onValueChanged(value)
            },
            label = { Text(label, color = Color.LightGray) },
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
fun ErrorText(loginUiState: LoginUiState) {
    if (loginUiState.isSuccess == false) {
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = loginUiState.errorMessage ?: "로그인 실패",
            color = AppColors.red2,
            fontSize = 13.sp,
            fontWeight = FontWeight(700),
        )
    }
}

@Composable
fun LoginOption() {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Text(
            text = stringResource(id = R.string.login_forgot_id),
            fontSize = 13.sp,
            color = AppColors.grey2,
            fontWeight = FontWeight.Bold,
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
            fontWeight = FontWeight.Bold,
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
            text = stringResource(id = R.string.login_signup),
            fontSize = 13.sp,
            color = AppColors.grey2,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun SocialLogin() {
    Text(
        text = "SNS 계정으로 로그인",
        color = AppColors.grey2,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.height(30.dp))
    Row(modifier = Modifier.height(36.dp)) {
        Image(painter = painterResource(id = R.drawable.ic_kakao_login), contentDescription = "")
        Spacer(modifier = Modifier.width(37.dp))
        Image(painter = painterResource(id = R.drawable.ic_naver_login), contentDescription = "")
        Spacer(modifier = Modifier.width(37.dp))
        Image(painter = painterResource(id = R.drawable.ic_google_login), contentDescription = "")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    FamilyMomentsTheme {
        LoginScreen(login = { _, _ -> }, loginUiState = LoginUiState())
    }
}
