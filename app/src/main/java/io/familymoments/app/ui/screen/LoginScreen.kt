package io.familymoments.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.repository.UserRepository
import io.familymoments.app.ui.theme.FamilyMomentsTheme
import io.familymoments.app.viewmodel.LoginViewModel

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.loginUser(username.text, password.text) }) {
            Text("Login")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    FamilyMomentsTheme {
        LoginScreen(viewModel = LoginViewModel(UserRepository(object : LoginService {
            override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
                return LoginResponse("token")
            }
        })))
    }
}