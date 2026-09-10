package com.tnc.domain.notification.model

enum class NotificationType {
    DOWNLOAD_COMPLETE,
    DAILY_RECIPE
}

data class AppNotification(

    val id: String,

    val type: NotificationType,

    val recipeId: String?,

    val recipeName: String?,

    val recipeNameTr: String? = null,

    val timestamp: Long,

    val isRead: Boolean

)
