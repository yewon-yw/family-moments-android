package io.familymoments.app.feature.joiningfamily.model

data class JoinFamilyResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val result:String = ""
)
