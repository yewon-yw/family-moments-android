package io.familymoments.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.familymoments.app.R
import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.repository.UserRepository
import io.familymoments.app.ui.theme.DarkPurple
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.ui.theme.LightPink
import io.familymoments.app.viewmodel.LoginViewModel


@ExperimentalMaterial3Api
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(86.dp))
        LoginLogo()
        Spacer(modifier = Modifier.height(49.dp))
        LoginForm(viewModel = viewModel)
        LoginOption()
        Spacer(modifier = Modifier.height(15.dp))
        SocialLogin()
    }
}

@Composable
fun LoginLogo() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_splash), contentDescription = "앱 로고"
        )
        Column(modifier = Modifier.padding(start = 5.dp)) {
            Text(
                text = stringResource(id = R.string.login_description_01),
                fontSize = 24.sp,
                color = Color(0xFF5B6380),
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = R.string.login_description_02),
                fontSize = 13.sp,
                color = Color(0xFFA9A9A9)
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun LoginForm(viewModel: LoginViewModel) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username", color = Color.LightGray) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightPink,
                    unfocusedContainerColor = LightPink,
                    disabledContainerColor = LightPink
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.LightGray) },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LightPink,
                    unfocusedContainerColor = LightPink,
                    disabledContainerColor = LightPink,
                )
            )
        }

        Spacer(modifier = Modifier.height(37.dp))
        Surface(shape = RoundedCornerShape(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier
                    .padding(vertical = 20.dp),
                onClick = {
                    viewModel.loginUser(username.text, password.text)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkPurple,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.login_login),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
            }

        }
    }
}

@Composable
fun LoginOption() {
    Row {
        Text(
            text = stringResource(id = R.string.login_forgot_id),
            fontSize = 13.sp,
            color = Color(0xFFA9A9A9),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.login_forgot_pw),
            fontSize = 13.sp,
            color = Color(0xFFA9A9A9),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.login_signup),
            fontSize = 13.sp,
            color = Color(0xFFA9A9A9),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SocialLogin() {
    Text(
        text = "SNS 계정으로 로그인",
        color = Color(0xFFA9A9A9),
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    FamilyMomentsTheme {
        LoginScreen(viewModel = LoginViewModel(UserRepository(object : LoginService {
            override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
                return LoginResponse("token")
            }
        })))
    }
}