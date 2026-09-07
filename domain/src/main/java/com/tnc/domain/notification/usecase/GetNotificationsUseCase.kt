package com.tnc.domain.notification.usecase

import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.notification.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow

class GetNotificationsUseCase(
    private val repository: NotificationRepository
) {

    operator fun invoke(): Flow<List<AppNotification>> = repository.getAll()

}
