package io.familymoments.app.core.network.dto.request

import androidx.compose.runtime.Immutable

@Immutable
data class CreateFamilyRequest(
    val familyName: String = "",
    val uploadCycle: Int? = 0
)
