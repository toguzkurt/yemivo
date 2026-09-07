package com.tnc.data.local.notification

import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.notification.model.NotificationType

fun NotificationEntity.toDomain(): AppNotification = AppNotification(
    id = id,
    type = when (type) {
        "download_complete" -> NotificationType.DOWNLOAD_COMPLETE
        else -> NotificationType.DAILY_RECIPE
    },
    recipeId = recipeId,
    recipeName = recipeName,
    timestamp = timestamp,
    isRead = isRead
)

fun NotificationType.toEntityType(): String = when (this) {
    NotificationType.DOWNLOAD_COMPLETE -> "download_complete"
    NotificationType.DAILY_RECIPE -> "daily_recipe"
}
