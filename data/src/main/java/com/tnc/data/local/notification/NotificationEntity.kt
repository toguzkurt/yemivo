package com.tnc.data.local.notification

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(

    @PrimaryKey
    val id: String,

    // "download_complete" | "daily_recipe" — see NotificationType for the domain-facing enum.
    val type: String,

    val recipeId: String?,

    val recipeName: String?,

    val recipeNameTr: String? = null,

    val timestamp: Long,

    val isRead: Boolean = false

)
