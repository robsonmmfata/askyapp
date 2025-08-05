package br.com.smartpos.asky.data.remote.network

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthEventManager {
    private val _unauthorizedEvent = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val unauthorizedEvent = _unauthorizedEvent.asSharedFlow()

    fun emitUnauthorized() {
        _unauthorizedEvent.tryEmit(Unit)
    }
}