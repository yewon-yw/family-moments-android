package io.familymoments.app.core.network.dto.request

data class EditPostRequest(
    val content:String,
    val urls:List<String>
)
