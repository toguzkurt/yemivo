package com.tnc.domain.notification.usecase

import com.tnc.domain.notification.repository.NotificationRepository

class MarkAllNotificationsReadUseCase(
    private val repository: NotificationRepository
) {

    suspend operator fun invoke() {
        repository.markAllRead()
    }

}
