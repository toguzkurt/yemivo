package com.tnc.yemivo.app.feature.notifications

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.notification.usecase.GetNotificationsUseCase
import com.tnc.domain.notification.usecase.MarkAllNotificationsReadUseCase
import kotlinx.coroutines.delay

class NotificationsViewModel(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val markAllNotificationsReadUseCase: MarkAllNotificationsReadUseCase
) : BaseViewModel<NotificationsUiState, NotificationsUiEffect>(initialState = NotificationsUiState()) {

    init {
        observeNotifications()
        markAllRead()
    }

    fun onEvent(
        event: NotificationsUiEvent
    ) {
        when (event) {

            is NotificationsUiEvent.NotificationClicked -> {
                event.notification.recipeId?.let { recipeId ->
                    sendEffect(NotificationsUiEffect.NavigateToRecipeDetail(recipeId))
                }
            }

        }
    }

    private fun observeNotifications() {

        launch {

            getNotificationsUseCase().collect { notifications ->

                setState { copy(notifications = notifications) }

            }

        }

    }

    private fun markAllRead() {
        launch {
            // Brief delay so the unread dot is still visible when the screen first appears,
            // rather than clearing before the user has actually seen it.
            delay(MARK_READ_DELAY_MS)
            markAllNotificationsReadUseCase()
        }
    }

    private companion object {
        const val MARK_READ_DELAY_MS = 1_200L
    }

}
