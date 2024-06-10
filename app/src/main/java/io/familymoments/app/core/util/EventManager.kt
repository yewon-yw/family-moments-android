package io.familymoments.app.core.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class EventManager {
    private val _events = MutableSharedFlow<UserEvent>()
    val events = _events.asSharedFlow()

    suspend fun sendEvent(event: UserEvent) {
        _events.emit(event)
    }
}

sealed class UserEvent {
    data object FamilyNameChanged : UserEvent()
    data object ProfileChanged: UserEvent()
}
