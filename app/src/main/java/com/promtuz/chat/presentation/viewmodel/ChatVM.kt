package com.promtuz.chat.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.promtuz.chat.data.dummy.DummyMessage
import com.promtuz.chat.data.dummy.dummyMessages
import com.promtuz.chat.data.dummy.randId
import com.promtuz.chat.domain.model.UiMessage
import com.promtuz.chat.domain.model.UiMessagePosition
import com.promtuz.chat.security.KeyManager
import com.promtuz.chat.utils.common.Time
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatVM(
    private val application: Application,
    private val appViewModel: AppVM
) : ViewModel() {
    private val context: Context get() = application.applicationContext

    private val _messages = MutableStateFlow(emptyList<UiMessage>())
    val messages: StateFlow<List<UiMessage>> = _messages.asStateFlow()

    private fun List<DummyMessage>.toUi(): List<UiMessage> = mapIndexed { i, m ->
        val prev = getOrNull(i - 1)
        val next = getOrNull(i + 1)

        val samePrev = prev?.isSent == m.isSent
        val sameNext = next?.isSent == m.isSent

        val position = when {
            samePrev && sameNext -> UiMessagePosition.Middle
            samePrev && !sameNext -> UiMessagePosition.Start
            !samePrev && sameNext -> UiMessagePosition.End
            else -> UiMessagePosition.Single
        }


        UiMessage(
            m.id, m.content, m.isSent, position, m.timestamp
        )
    }

    fun dispatchMessage(content: String) {
        _messages.update {
            listOf(
                UiMessage(
                    randId(),
                    content,
                    true,
                    UiMessagePosition.End,
                    Time.now()
                )
            ) + it
        }
    }

    init {
        _messages.update { it + dummyMessages.toUi() }
    }
}