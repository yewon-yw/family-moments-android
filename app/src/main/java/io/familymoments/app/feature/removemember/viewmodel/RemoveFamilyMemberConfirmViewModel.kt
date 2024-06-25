package io.familymoments.app.feature.removemember.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.graph.Route
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.repository.FamilyRepository
import javax.inject.Inject

@HiltViewModel
class RemoveFamilyMemberConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val familyRepository: FamilyRepository,
    private val userInfoPreferencesDataSource: UserInfoPreferencesDataSource
): BaseViewModel() {
    private val userIds: Array<String> = checkNotNull(savedStateHandle[Route.RemoveFamilyMember.userIdsArg])
    var userIdsList: List<String> = userIds.asList()

}
