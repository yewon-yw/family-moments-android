package io.familymoments.app.core.network.dto.response

data class CheckEmailResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: String,
)
