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

}
