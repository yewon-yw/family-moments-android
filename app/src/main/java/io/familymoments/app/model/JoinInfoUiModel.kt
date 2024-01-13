package io.familymoments.app.model

import android.graphics.Bitmap

data class JoinInfoUiModel(
        val id: String,
        val password: String,
        val name: String,
        val email: String,
        val birthDay: String,
        val nickname: String,
        val bitmap: Bitmap,
)