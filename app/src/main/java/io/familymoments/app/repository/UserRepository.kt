package io.familymoments.app.repository

import io.familymoments.app.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun loginUser(username: String, password: String): Flow<LoginResponse>

}