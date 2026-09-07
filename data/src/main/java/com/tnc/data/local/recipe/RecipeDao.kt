package com.tnc.data.local.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY name ASC")
    fun getAll(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getById(id: String): Flow<RecipeEntity?>

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT isFavorite FROM recipes WHERE id = :id")
    suspend fun isFavorite(id: String): Boolean?

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)

    @Query("SELECT isDownloaded FROM recipes WHERE id = :id")
    suspend fun isDownloaded(id: String): Boolean?

    @Query("UPDATE recipes SET isDownloaded = :isDownloaded WHERE id = :id")
    suspend fun setDownloaded(id: String, isDownloaded: Boolean)

    @Query("SELECT * FROM recipes ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandom(): RecipeEntity?

}
