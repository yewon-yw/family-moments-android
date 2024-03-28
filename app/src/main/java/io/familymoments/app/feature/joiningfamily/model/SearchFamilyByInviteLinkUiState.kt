package io.familymoments.app.feature.joiningfamily.model

data class JoinFamilyUiState(
    val searchFamilyByInviteLinkUiState: SearchFamilyByInviteLinkUiState = SearchFamilyByInviteLinkUiState(),
    val joinFamilyExecuteUiState: JoinFamilyExecuteUiState = JoinFamilyExecuteUiState()
    )

data class SearchFamilyByInviteLinkUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = "",
    val result: SearchFamilyByInviteLinkResult? = null
)

data class JoinFamilyExecuteUiState(
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val errorMessage: String? = "",
    val result: String? = null
)
