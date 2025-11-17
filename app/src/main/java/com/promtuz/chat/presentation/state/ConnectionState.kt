package com.promtuz.chat.presentation.state

import androidx.annotation.StringRes
import com.promtuz.chat.R

sealed class ConnectionState(@param:StringRes val text: Int) {
    /**
     * App hasn’t started any networking yet.
     */
    object Idle : ConnectionState(R.string.state_idle)

    /**
     * App is contacting resolver OR loading cached relays OR picking one.
     */
    object Resolving : ConnectionState(R.string.state_resolving)

    /**
     * QUIC dialing a relay.
     */
    object Connecting : ConnectionState(R.string.state_connecting)

    /**
     * Custom crypto handshake in-progress to prove identity.
     */
    object Handshaking : ConnectionState(R.string.state_handshaking)

    /**
     * Authenticated and can send/receive messages
     */
    object Connected : ConnectionState(R.string.state_connected)

    /**
     * Something went wrong (resolver failed, relay dead, handshake failed).
     */
    object Failed : ConnectionState(R.string.state_failed)

    /**
     * App is looking for another relay after the previous died.
     */
    object Reconnecting : ConnectionState(R.string.state_reconnecting)
}