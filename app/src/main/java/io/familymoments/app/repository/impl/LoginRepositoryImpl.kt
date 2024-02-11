package io.familymoments.app.repository.impl

import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.network.Resource
import io.familymoments.app.repository.LoginRepository
import io.familymoments.app.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.Headers
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val tokenRepository: TokenRepository
) : LoginRepository {
    override suspend fun loginUser(
        username: String,
        password: String,
    ): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = loginService.loginUser(LoginRequest(username, password))
            val responseBody = response.body() ?: LoginResponse()

            if (responseBody.isSuccess) {
                saveAccessToken(response.headers())
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }

        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun checkValidation(): Flow<Resource<Unit>> {

        return flow {
            emit(Resource.Loading)
            val response = loginService.checkValidation()
            if (response.code() == 200) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    private suspend fun saveAccessToken(headers: Headers) {
        val accessToken = headers[KEY_ACCESS_TOKEN]
            ?: throw IllegalStateException(GET_ACCESS_TOKEN_ERROR)
        tokenRepository.saveAccessToken(accessToken)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "X-AUTH-TOKEN"
        private const val GET_ACCESS_TOKEN_ERROR = "Fail to get Access Token"
    }
}
