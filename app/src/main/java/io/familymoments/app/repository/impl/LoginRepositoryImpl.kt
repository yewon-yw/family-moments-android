package io.familymoments.app.repository.impl

import io.familymoments.app.model.LoginRequest
import io.familymoments.app.model.LoginResponse
import io.familymoments.app.model.TokenResponse
import io.familymoments.app.network.LoginService
import io.familymoments.app.network.Resource
import io.familymoments.app.repository.LoginRepository
import io.familymoments.app.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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
                saveToken(response.headers())
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }

        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun checkValidation(): Flow<Resource<TokenResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = loginService.checkValidation()
            if (response.isSuccess) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Fail(Throwable(response.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    private suspend fun saveToken(headers: okhttp3.Headers) {
        val accessToken = headers[KEY_ACCESS_TOKEN]
            ?: throw IllegalStateException(GET_ACCESS_TOKEN_ERROR)
        tokenRepository.saveAccessToken(accessToken)

        val refreshToken = headers[KEY_REFRESH_TOKEN]?.split(";")?.get(0)?.split("=")?.get(1)
            ?: throw IllegalStateException(GET_REFRESH_TOKEN_ERROR)
        tokenRepository.saveRefreshToken(refreshToken)
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "X-AUTH-TOKEN"
        private const val KEY_REFRESH_TOKEN = "Set-Cookie"
        private const val GET_ACCESS_TOKEN_ERROR = "Fail to get Access Token"
        private const val GET_REFRESH_TOKEN_ERROR = "Fail to get Refresh Token"
    }
}
