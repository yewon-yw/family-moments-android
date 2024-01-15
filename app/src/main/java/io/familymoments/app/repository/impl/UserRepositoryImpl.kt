package io.familymoments.app.repository.impl

import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.network.Resource
import io.familymoments.app.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
        private val loginService: LoginService,
) : UserRepository {
    override suspend fun loginUser(
            username: String,
            password: String,
    ): Flow<Resource<LoginResponse>> {
        return flow {
            val result = loginService.loginUser(LoginRequest(username, password))
            if (result.isSuccess) {
                emit(Resource.Success(result))
            } else {
                emit(Resource.Fail(Throwable(result.message)))
            }

        }.catch { e ->
            e.printStackTrace()
        }
    }
}