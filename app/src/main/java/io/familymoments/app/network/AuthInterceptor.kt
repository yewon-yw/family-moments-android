package io.familymoments.app.network

import io.familymoments.app.repository.TokenRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = kotlin.runCatching {
            tokenRepository.loadAccessToken()
        }.getOrElse {
            return errorResponse(chain.request())
        }
        val request = chain.request().newBuilder().addHeader(AUTHORIZATION, token).build()
        return chain.proceed(request)
    }

    private fun errorResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .message("")
            .body("".toResponseBody(null))
            .build()
    }

    companion object {
        private const val AUTHORIZATION = "X-AUTH-TOKEN"
    }
}
