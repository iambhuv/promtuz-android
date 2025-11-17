package com.promtuz.chat.data.remote.resolver

import com.promtuz.chat.data.remote.dto.RelayDescriptor

class ResolverClient(

) {
    suspend fun fetchRelays(): List<RelayDescriptor> {
//        val conn = quic.connect(address, "resolver-host")
//        val (tx, rx) = conn.openBi()
//
//        tx.writeCbor(GetRelays)
//        tx.flush()
//
//        val bytes = rx.readToEnd()
//        return decodeCbor<RelayListResponse>(bytes).relays
        return emptyList()
    }
}