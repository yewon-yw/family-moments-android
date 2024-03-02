package io.familymoments.app.feature.calendar.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class CalendarDayViewModel @Inject constructor(
    private val postRepository: PostRepository
) : BaseViewModel() {

}
