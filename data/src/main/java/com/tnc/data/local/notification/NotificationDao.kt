package com.tnc.data.local.notification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAll(): Flow<List<NotificationEntity>>

    @Insert
    suspend fun insert(notification: NotificationEntity)

    @Query("SELECT * FROM notifications WHERE type = :type ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestByType(type: String): NotificationEntity?

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllRead()

}
