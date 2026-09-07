package com.tnc.domain.recipe.repository

import com.tnc.domain.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getRecipes(): Flow<List<Recipe>>

    fun getRecipeById(
        id: String
    ): Flow<Recipe?>

    suspend fun toggleFavorite(
        id: String
    )

    /**
     * Returns the resulting isDownloaded state so callers can tell an on-toggle (worth a
     * "download complete" notification) from an off-toggle (isn't).
     */
    suspend fun toggleDownload(
        id: String
    ): Boolean

}
