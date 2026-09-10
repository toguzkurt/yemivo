package com.tnc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tnc.data.local.notification.NotificationDao
import com.tnc.data.local.notification.NotificationEntity
import com.tnc.data.local.recipe.RecipeDao
import com.tnc.data.local.recipe.RecipeEntity
import com.tnc.data.local.shoppinglist.ShoppingListDao
import com.tnc.data.local.shoppinglist.ShoppingListItemEntity

@Database(
    entities = [
        RecipeEntity::class,
        ShoppingListItemEntity::class,
        NotificationEntity::class
    ],
    version = 5,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun notificationDao(): NotificationDao

}
