package com.tnc.domain.notification.repository

import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {

    fun getAll(): Flow<List<AppNotification>>

    suspend fun notifyDownloadComplete(
        recipe: Recipe
    )

    /**
     * No-ops if a daily-recipe notification already exists for today — safe to call on every
     * app start.
     */
    suspend fun generateDailyRecipeNotificationIfNeeded()

    suspend fun markAllRead()

}
