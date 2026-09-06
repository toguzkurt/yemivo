package com.tnc.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tnc.data.local.recipe.RecipeDao
import com.tnc.data.local.recipe.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}
