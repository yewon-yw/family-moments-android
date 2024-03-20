package io.familymoments.app.core.network.util

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.Locale

fun createPostInfoRequestBody(content: String): RequestBody {
    val json = """{"content": "$content"}"""
    return json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}

fun createImageParts(imageFiles: List<File>): List<MultipartBody.Part> {
    return imageFiles.mapIndexed { index, file ->
        // 파일 확장자에 따른 MIME 타입 결정
        val mimeType = when (file.extension.lowercase(Locale.getDefault())) {
            "png" -> "image/png"
            "jpeg", "jpg" -> "image/jpeg"
            else -> "application/octet-stream" // 확장자가 지원되지 않는 경우 기본값
        }

        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
        MultipartBody.Part.createFormData("img${index + 1}", file.name, requestFile)
    }
}
