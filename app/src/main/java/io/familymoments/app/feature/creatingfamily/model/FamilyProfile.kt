package io.familymoments.app.feature.creatingfamily.model

import java.io.File

data class FamilyProfile(
    val name: String = "",
    val imgFile: File? = null,
    val uploadCycle: Int? = null
)
