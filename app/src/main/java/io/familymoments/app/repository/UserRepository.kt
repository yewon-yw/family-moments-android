package io.familymoments.app.repository

import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val loginService: LoginService) {
    suspend fun loginUser(username: String, password: String): Flow<LoginResponse> {
        return flow {
            val response = loginService.loginUser(LoginRequest(username, password))
            emit(loginService.loginUser(LoginRequest(username, password)))
        }.catch { e ->
            e.printStackTrace()
        }
    }
}