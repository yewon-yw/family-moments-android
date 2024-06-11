package io.familymoments.app.core.network.dto.response

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: String
)
