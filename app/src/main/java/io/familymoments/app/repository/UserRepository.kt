package io.familymoments.app.repository

import io.familymoments.app.model.response.LoginResponse
import io.familymoments.app.network.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loginUser(username: String, password: String): Flow<Resource<LoginResponse>>
    suspend fun checkValidation(): Flow<Resource<Unit>>
}
