package com.tnc.data.repository

import com.tnc.data.local.notification.NotificationDao
import com.tnc.data.local.notification.NotificationEntity
import com.tnc.data.local.notification.toDomain
import com.tnc.data.local.notification.toEntityType
import com.tnc.data.local.recipe.RecipeDao
import com.tnc.domain.notification.model.AppNotification
import com.tnc.domain.notification.model.NotificationType
import com.tnc.domain.notification.repository.NotificationRepository
import com.tnc.domain.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

class NotificationRepositoryImpl(
    private val notificationDao: NotificationDao,
    private val recipeDao: RecipeDao
) : NotificationRepository {

    override fun getAll(): Flow<List<AppNotification>> =
        notificationDao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun notifyDownloadComplete(
        recipe: Recipe
    ) {
        notificationDao.insert(
            NotificationEntity(
                id = UUID.randomUUID().toString(),
                type = NotificationType.DOWNLOAD_COMPLETE.toEntityType(),
                recipeId = recipe.id,
                recipeName = recipe.name,
                recipeNameTr = recipe.nameTr,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    override suspend fun generateDailyRecipeNotificationIfNeeded() {

        val latest = notificationDao.getLatestByType(NotificationType.DAILY_RECIPE.toEntityType())

        if (latest != null && isSameDay(latest.timestamp, System.currentTimeMillis())) return

        val recipe = recipeDao.getRandom() ?: return

        notificationDao.insert(
            NotificationEntity(
                id = UUID.randomUUID().toString(),
                type = NotificationType.DAILY_RECIPE.toEntityType(),
                recipeId = recipe.id,
                recipeName = recipe.name,
                recipeNameTr = recipe.nameTr,
                timestamp = System.currentTimeMillis()
            )
        )

    }

    override suspend fun markAllRead() {
        notificationDao.markAllRead()
    }

    private fun isSameDay(
        firstMillis: Long,
        secondMillis: Long
    ): Boolean {
        val zone = ZoneId.systemDefault()
        val firstDate = Instant.ofEpochMilli(firstMillis).atZone(zone).toLocalDate()
        val secondDate = Instant.ofEpochMilli(secondMillis).atZone(zone).toLocalDate()
        return firstDate == secondDate
    }

}
