package com.promtuz.chat.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ResolverSeeds(
    @Serializable val seeds: List<ResolverSeed>
)

@Serializable
data class ResolverSeed(
    @Serializable val host: String, @Serializable val port: UShort
)