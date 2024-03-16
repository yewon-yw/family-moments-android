package io.familymoments.app.feature.signup.model

import android.graphics.Bitmap

data class SignUpInfoUiModel(
    val id: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val birthDay: String = "",
    val nickname: String = "",
    val bitmap: Bitmap,
)
