package io.familymoments.app.core.network.dto.request

import androidx.compose.runtime.Immutable
import java.io.File

@Immutable
data class FamilyProfile(
    val name: String = "",
    val imgFile: File? = null,
    val uploadCycle: Int? = null
)
