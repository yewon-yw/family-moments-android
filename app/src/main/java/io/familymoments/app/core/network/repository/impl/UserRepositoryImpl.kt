package io.familymoments.app.core.network.repository.impl

import io.familymoments.app.core.network.AuthErrorManager
import io.familymoments.app.core.network.AuthErrorResponse
import io.familymoments.app.core.network.HttpResponse
import io.familymoments.app.core.network.LoginType
import io.familymoments.app.core.network.Resource
import io.familymoments.app.core.network.api.UserService
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.dto.request.CheckIdExistRequest
import io.familymoments.app.core.network.dto.request.FindIdRequest
import io.familymoments.app.core.network.dto.request.FindPwdRequest
import io.familymoments.app.core.network.dto.request.LoginRequest
import io.familymoments.app.core.network.dto.request.ModifyPasswordRequest
import io.familymoments.app.core.network.dto.request.ModifyPwdInFindPwdRequest
import io.familymoments.app.core.network.dto.request.ProfileEditRequest
import io.familymoments.app.core.network.dto.request.SendEmailRequest
import io.familymoments.app.core.network.dto.request.SocialSignInRequest
import io.familymoments.app.core.network.dto.response.ApiResponse
import io.familymoments.app.core.network.dto.response.CheckIdExistResponse
import io.familymoments.app.core.network.dto.response.FindIdResponse
import io.familymoments.app.core.network.dto.response.FindPwdResponse
import io.familymoments.app.core.network.dto.response.LoginResponse
import io.familymoments.app.core.network.dto.response.LogoutResponse
import io.familymoments.app.core.network.dto.response.ModifyPasswordResponse
import io.familymoments.app.core.network.dto.response.ModifyPwdInFindPwdResponse
import io.familymoments.app.core.network.dto.response.ProfileEditResponse
import io.familymoments.app.core.network.dto.response.SearchMemberResponse
import io.familymoments.app.core.network.dto.response.SendEmailResponse
import io.familymoments.app.core.network.dto.response.SocialSignInResult
import io.familymoments.app.core.network.dto.response.UserProfile
import io.familymoments.app.core.network.dto.response.getResourceFlow
import io.familymoments.app.core.network.repository.UserRepository
import io.familymoments.app.core.util.DEFAULT_FAMILY_ID_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.Headers
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource,
    private val authErrorManager: AuthErrorManager
) : UserRepository {
    override suspend fun loginUser(
        username: String,
        password: String,
        fcmToken: String
    ): Flow<Resource<LoginResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.loginUser(LoginRequest(username, password), fcmToken)
            val responseBody = response.body() ?: LoginResponse()

            if (responseBody.isSuccess) {
                saveTokensFrom(response.headers())
                val familyId: Long? = responseBody.result.familyId
                userInfoPreferencesDataSource.saveFamilyId(familyId ?: DEFAULT_FAMILY_ID_VALUE)

                loadUserProfile(familyId).collect { result ->
                    if (result is Resource.Success) {
                        userInfoPreferencesDataSource.saveLoginType(LoginType.NORMAL)
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
        userInfoPreferencesDataSource.saveAccessToken(accessToken)
    }

    override suspend fun reissueAccessToken(): Flow<Resource<Unit>> {
        return flow {
            emit(Resource.Loading)
            // get refresh token from shared preferences
            val refreshToken = userInfoPreferencesDataSource.loadRefreshToken()
            if (refreshToken.isEmpty()) {
                emit(Resource.Fail(Throwable("Not Signed User")))
                return@flow
            }

            val response = userService.reissueAccessToken(refreshToken)
            if (response.code() == HttpResponse.SUCCESS) {
                // refresh token 을 기반으로 access token 재발급 성공
                kotlin.runCatching {
                    saveAccessToken(response.headers())
                }.onSuccess {
                    emit(Resource.Success(Unit))
                }.onFailure {
                    emit(Resource.Fail(it))

                    // GET_ACCESS_TOKEN_ERROR 발생 시 로그인 화면으로 이동
                    authErrorManager.emitNeedNavigateToLogin()
                }
            } else if (response.code() == HttpResponse.REFRESH_TOKEN_EXPIRED) {
                // refresh token 까지 만로 됐다는 것을 뜻함. 따라서 즉 재로그인 필요
                // screen 에서 RefreshTokenExpiration 오류 발생 확인되면 로그인 화면으로 이동
                emit(Resource.Fail(AuthErrorResponse.RefreshTokenExpiration))
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(Throwable(e.message)))
        }
    }

    override suspend fun loadUserProfile(familyId: Long?): Flow<Resource<ApiResponse<UserProfile>>> {
        val response = userService.loadUserProfile(familyId)
        return getResourceFlow(response)
    }

    override suspend fun modifyPassword(modifyPasswordRequest: ModifyPasswordRequest): Flow<Resource<ModifyPasswordResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.modifyPassword(modifyPasswordRequest)
            val responseBody =
                response.body() ?: ModifyPasswordResponse()
            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else if (responseBody.code == HttpResponse.INCORRECT_CURRENT_PASSWORD || responseBody.code == HttpResponse.NEW_PASSWORD_SAME_AS_CURRENT) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun searchMember(
        keyword: String,
        newFamily: Boolean
    ): Flow<Resource<SearchMemberResponse>> {
        return flow {
            emit(Resource.Loading)
            val familyId = if (newFamily) null else userInfoPreferencesDataSource.loadFamilyId()
            val response = userService.searchMember(keyword, familyId)
            val responseBody = response.body() ?: SearchMemberResponse()

            if (responseBody.isSuccess) {
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun editUserProfile(
        profileEditRequest: ProfileEditRequest,
        profileImg: MultipartBody.Part
    ): Flow<Resource<ProfileEditResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.editUserProfile(profileEditRequest, profileImg)
            val responseBody = response.body() ?: ProfileEditResponse()
            if (responseBody.isSuccess) {
                userInfoPreferencesDataSource.updateUserProfile(responseBody.result)
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun executeSocialSignIn(
        type: String,
        token: String,
        fcmToken: String
    ): Flow<Resource<SocialSignInResult>> = flow {
        emit(Resource.Loading)

        val response = userService.executeSocialSignIn(SocialSignInRequest(type), token, fcmToken)

        if (response.code() == 200) {
            saveTokensFrom(response.headers())

            val body = response.body()!!
            if (body.isSuccess) {
                body.result.familyId?.let {
                    userInfoPreferencesDataSource.saveFamilyId(it)
                    loadUserProfile(it).collect { result ->
                        if (result is Resource.Success) {
                            userInfoPreferencesDataSource.saveUserProfile(result.data.result)
                        }
                        if (result is Resource.Fail) {
                            emit(Resource.Fail(Throwable(result.exception.message)))
                        }
                    }
                }
                userInfoPreferencesDataSource.saveLoginType(type)
                emit(Resource.Success(body.result))
            } else {
                emit(Resource.Fail(Throwable(body.message)))
            }
        } else {
            emit(Resource.Fail(Throwable("Failed to sign in")))
        }
    }.catch { e ->
        emit(Resource.Fail(e))
    }


    private suspend fun saveTokensFrom(headers: Headers) {
        headers[KEY_ACCESS_TOKEN]?.let {
            userInfoPreferencesDataSource.saveAccessToken(it)
        }

        headers[KEY_REFRESH_TOKEN]?.let {
            userInfoPreferencesDataSource.saveRefreshToken(it)
        }
    }

    override suspend fun checkIdExist(userId: String): Flow<Resource<CheckIdExistResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.checkIdExist(CheckIdExistRequest(userId))
            if (response.code() == HttpResponse.SUCCESS) {
                val responseBody = response.body() ?: CheckIdExistResponse()
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun sendEmail(name: String, email: String): Flow<Resource<SendEmailResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.sendEmail(SendEmailRequest(name, email))
            if (response.code() == HttpResponse.SUCCESS) {
                val responseBody = response.body() ?: SendEmailResponse()
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun findPwd(name: String, email: String, code: String): Flow<Resource<FindPwdResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.findPwd(FindPwdRequest(name, email, code))
            if (response.code() == HttpResponse.SUCCESS) {
                val responseBody = response.body() ?: FindPwdResponse()
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun modifyPwdInFindPwd(
        id: String,
        password: String,
        passwordConfirm: String
    ): Flow<Resource<ModifyPwdInFindPwdResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.modifyPwdInFindPwd(id, ModifyPwdInFindPwdRequest(password, passwordConfirm))
            if (response.code() == HttpResponse.SUCCESS) {
                val responseBody = response.body() ?: ModifyPwdInFindPwdResponse()
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun findId(name: String, email: String, code: String): Flow<Resource<FindIdResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.findId(FindIdRequest(name, email, code))
            if (response.code() == HttpResponse.SUCCESS) {
                val responseBody = response.body() ?: FindIdResponse()
                if (responseBody.isSuccess) {
                    emit(Resource.Success(responseBody))
                } else {
                    emit(Resource.Fail(Throwable(responseBody.message)))
                }
            } else {
                emit(Resource.Fail(Throwable(response.message())))
            }
        }.catch { e ->
            emit(Resource.Fail(e))
        }
    }

    override suspend fun deleteAccount(): Flow<Resource<ApiResponse<String>>> {
        val response = userService.deleteAccount()
        return getResourceFlow(response)
    }

    override suspend fun reportUser(userId: String): Flow<Resource<ApiResponse<String>>> {
        val response = userService.reportUser(userId)
        return getResourceFlow(response)
    }

    override suspend fun logoutUser(): Flow<Resource<LogoutResponse>> {
        return flow {
            emit(Resource.Loading)
            val response = userService.logoutUser()
            val responseBody = response.body() ?: LogoutResponse()
            if (responseBody.isSuccess) {
                userInfoPreferencesDataSource.resetPreferencesData()
                emit(Resource.Success(responseBody))
            } else {
                emit(Resource.Fail(Throwable(responseBody.message)))
            }
        }.catch { e ->
            emit(Resource.Fail(Throwable(e)))
        }
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "X-AUTH-TOKEN"
        private const val KEY_REFRESH_TOKEN = "REFRESH-TOKEN"
        private const val GET_ACCESS_TOKEN_ERROR = "Fail to get Access Token"
    }
}
