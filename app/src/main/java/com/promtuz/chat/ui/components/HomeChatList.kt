package com.promtuz.chat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.promtuz.chat.data.dummy.dummyChats
import com.promtuz.chat.ui.util.groupedRoundShape
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeChatList(
    innerPadding: PaddingValues
) {
    val direction = LocalLayoutDirection.current
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val state = rememberPullToRefreshState(
//        refreshing = loading,
//        onRefresh = { }
    )

    PullToRefreshBox(loading, {
        scope.launch {
            loading = true
            delay(1000)
            loading = false
        }
    }) {
        LazyColumn(
            Modifier
                .padding(
                    start = innerPadding.calculateLeftPadding(direction),
                    end = innerPadding.calculateRightPadding(direction),
                    top = 0.dp,
                    bottom = 0.dp
                )
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                Spacer(Modifier.height(innerPadding.calculateTopPadding()))
            }

            itemsIndexed(dummyChats) { index, chat ->
                HomeChatListItem(chat, groupedRoundShape(index, dummyChats.size))
            }


            item {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
