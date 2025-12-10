package com.promtuz.chat.presentation.state

import androidx.annotation.StringRes
import com.promtuz.chat.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// @formatter:off

@Serializable
enum class ConnectionState(@param:StringRes val text: Int) {
    /**
     * Internet connection is not available.
     */
    @SerialName("Offline") Offline(R.string.state_offline),

    /**
     * App hasn’t started any networking yet.
     */
    @SerialName("Idle") Idle(R.string.state_idle),

    /**
     * App is contacting resolver OR loading cached relays OR picking one.
     */
    @SerialName("Resolving") Resolving(R.string.state_resolving),

    /**
     * QUIC dialing a relay.
     */
    @SerialName("Connecting") Connecting(R.string.state_connecting),

    /**
     * Custom crypto handshake in-progress to prove identity.
     */
    @SerialName("Handshaking") Handshaking(R.string.state_handshaking),

    /**
     * Authenticated and can send/receive messages
     */
    @SerialName("Connected") Connected(R.string.state_connected),

    /**
     * Something went wrong (resolver failed, relay dead, handshake failed).
     */
    @SerialName("Failed") Failed(R.string.state_failed),

    /**
     * App is looking for another relay after the previous died.
     */
    @SerialName("Reconnecting") Reconnecting(R.string.state_reconnecting),
}