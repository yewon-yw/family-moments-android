package io.familymoments.app.repository

import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.network.Resource
import io.familymoments.app.network.execute
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val loginService: LoginService) {

    suspend fun loginUser(username: String, password: String): Flow<Resource<LoginResponse>> {
        return execute { loginService.loginUser(LoginRequest(username, password)) }
    }

}