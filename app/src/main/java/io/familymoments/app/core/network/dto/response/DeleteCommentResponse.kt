package io.familymoments.app.core.network.dto.response

data class DeleteCommentResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result: String = ""
)
