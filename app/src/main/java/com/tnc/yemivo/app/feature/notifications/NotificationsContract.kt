package com.tnc.yemivo.app.feature.notifications

import com.tnc.domain.notification.model.AppNotification

data class NotificationsUiState(

    val notifications: List<AppNotification> = emptyList()

)

sealed interface NotificationsUiEvent {

    data class NotificationClicked(
        val notification: AppNotification
    ) : NotificationsUiEvent

}

sealed interface NotificationsUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : NotificationsUiEffect

}
