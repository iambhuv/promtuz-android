package com.promtuz.chat.data.remote.dto

import com.promtuz.chat.utils.serialization.InetSocketAddressAsString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.net.InetSocketAddress


@Serializable
sealed interface ClientResponse

@Serializable
@SerialName("GetRelays")
data class ResolvedRelays(
    val relays: List<RelayDescriptor>
) : ClientResponse

@Serializable
data class ClientResponseDto(
    @SerialName("GetRelays")
    val content: ResolvedRelays
)


@Serializable
data class RelayDescriptor(
    val id: String,
    @Serializable(with = InetSocketAddressAsString::class)
    val addr: InetSocketAddress
)