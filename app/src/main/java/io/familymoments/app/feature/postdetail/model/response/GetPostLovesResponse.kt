package io.familymoments.app.feature.postdetail.model.response

data class GetPostLovesResponse(
    val isSuccess: Boolean = false,
    val code: Int = 0,
    val message: String = "",
    val results: List<GetPostLovesByIndexResult> = listOf()
)

data class GetPostLovesByIndexResult(
    val nickname: String = "",
    val profileImg: String = ""
)
