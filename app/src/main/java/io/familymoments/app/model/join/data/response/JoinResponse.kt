package io.familymoments.app.model.join.data.response

data class JoinResponse(
        val isSuccess: Boolean,
        val code: Int,
        val message: String,
        val result: JoinResult
)

