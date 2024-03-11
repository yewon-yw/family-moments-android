package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.UserService
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.model.AuthErrorResponse
import io.familymoments.app.core.network.model.UserProfileResponse
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.feature.creatingfamily.model.response.SearchMemberResponse
import io.familymoments.app.feature.login.model.request.LoginRequest
import io.familymoments.app.feature.login.model.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.Headers
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
) : UserRepository {
    override suspend fun loginUser(
        username: String,
        password: String
    ): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.loginUser(LoginRequest(username, password))
            val responseBody = response.body() ?: LoginResponse()

            if (responseBody.isSuccess) {
                saveAccessToken(response.headers())
                val familyId: Long? = responseBody.loginResult.familyId
                if (familyId != null) {
                    userInfoPreferencesDataSource.saveFamilyId(familyId)
                }
                loadUserProfile(familyId).collect { result ->
                    if (result is Resource.Success) {
                        userInfoPreferencesDataSource.saveUserProfile(result.data.result)
                    }
                    if (result is Resource.Fail) {
                        emit(Resource.Fail(Throwable(result.exception.message)))
                    }

                }
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }

        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    private suspend fun saveAccessToken(headers: Headers) {
        val accessToken = headers[KEY_ACCESS_TOKEN]
            ?: throw IllegalStateException(GET_ACCESS_TOKEN_ERROR)
        Timber.tag("hkhk").d("accessToken: %s", accessToken)
        userInfoPreferencesDataSource.saveAccessToken(accessToken)
    }

    override suspend fun reissueAccessToken(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.reissueAccessToken()
            if (response.code() == 200) {
                saveAccessToken(response.headers())
                emit(Resource.Success(Unit))
            } else if (response.code() == 471) {
                // 로그인 화면 전환
                emit(Resource.Fail(AuthErrorResponse.RefreshTokenExpiration))
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(Throwable(e.message)))
        }
    }

    override suspend fun loadUserProfile(familyId: Long?): Flow<Resource<UserProfileResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.loadUserProfile(familyId)
            val responseBody = response.body() ?: UserProfileResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }

        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun searchMember(keyword: String): Flow<Resource<SearchMemberResponse>> {
        return flow {
            emit(Resource.Loading)
//            val familyId = userInfoPreferencesDataSource.loadFamilyId()

            val response = userService.searchMember(keyword, null)
            val responseBody = response.body() ?: SearchMemberResponse()

            if (response.code() == 200) {
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    // 사용자 인증 오류
                    reissueAccessToken().collect { result ->
                        if (result is Resource.Success) {
                            // 새 access token 발급 후 다시 api 실행
                            searchMember(keyword)
                        }
                        if (result is Resource.Fail) emit(Resource.Fail(result.exception))
                    }
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }


        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "X-AUTH-TOKEN"
        private const val GET_ACCESS_TOKEN_ERROR = "Fail to get Access Token"
    }
}
