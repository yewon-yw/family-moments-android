package io.familymoments.app.feature.join.model.response

data class JoinResponse(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: JoinResult
)

