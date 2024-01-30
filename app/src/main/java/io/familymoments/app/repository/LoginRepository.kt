package io.familymoments.app.repository

import io.familymoments.app.model.LoginResponse
import io.familymoments.app.model.TokenResponse
import io.familymoments.app.network.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginUser(username: String, password: String): Flow<Resource<LoginResponse>>
    suspend fun checkValidation(): Flow<Resource<TokenResponse>>
}
