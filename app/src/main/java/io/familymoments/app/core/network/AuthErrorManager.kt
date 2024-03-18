package io.familymoments.app.core.network

import io.familymoments.app.core.util.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthErrorManager {

    private val _needReissueToken = MutableSharedFlow<Event<Unit>>()
    val needReissueToken = _needReissueToken.asSharedFlow()

    private val _needNavigateToLogin = MutableSharedFlow<Event<Unit>>()
    val needNavigateToLogin = _needNavigateToLogin.asSharedFlow()

    suspend fun emitNeedReissueToken() {
        _needReissueToken.emit(Event(Unit))
    }

    suspend fun emitNeedNavigateToLogin() {
        _needNavigateToLogin.emit(Event(Unit))
    }
}
