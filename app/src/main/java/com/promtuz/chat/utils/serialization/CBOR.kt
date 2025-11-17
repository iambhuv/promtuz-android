package com.promtuz.chat.utils.serialization

import com.promtuz.chat.data.remote.dto.ClientResponse
import com.promtuz.chat.data.remote.dto.GetRelays
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.modules.SerializersModule

@OptIn(ExperimentalSerializationApi::class)
object AppCbor {
    val instance: Cbor = Cbor {
        ignoreUnknownKeys = true
        encodeDefaults = true
        useDefiniteLengthEncoding = true

        preferCborLabelsOverNames
    }
}
