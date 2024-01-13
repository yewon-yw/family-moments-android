package io.familymoments.app.model

data class CheckIdResponse(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: String,
)

data class CheckEmailResponse(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: String,
)

data class JoinResponse(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result:JoinResult
)

data class JoinResult(
        val email: String,
        val nickname: String,
        val profileImg: String,
)