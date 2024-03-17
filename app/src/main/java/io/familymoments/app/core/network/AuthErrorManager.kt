package io.familymoments.app.core.network

import io.familymoments.app.core.util.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthErrorManager {
    private val _token403Error = MutableSharedFlow<Event<Unit>>()
    val token403Error = _token403Error.asSharedFlow()

    private val _getAccessTokenError = MutableSharedFlow<Event<Unit>>()
    val getAccessTokenError = _getAccessTokenError.asSharedFlow()

    private val _refreshTokenExpiration = MutableSharedFlow<Event<Unit>>()
    val refreshTokenExpiration = _refreshTokenExpiration.asSharedFlow()

    suspend fun emitToken403Error() {
        _token403Error.emit(Event(Unit))
    }

    suspend fun emitGetAccessTokenError() {
        _getAccessTokenError.emit(Event(Unit))
    }

    suspend fun emitRefreshTokenExpiration() {
        _refreshTokenExpiration.emit(Event(Unit))
    }
}
