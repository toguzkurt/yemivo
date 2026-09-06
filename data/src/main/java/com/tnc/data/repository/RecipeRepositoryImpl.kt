package com.tnc.data.repository

import com.tnc.data.local.recipe.sampleRecipes
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/**
 * In-memory implementation, seeded with [sampleRecipes]. Stands in for a future Room/remote
 * data source — the domain layer above it (RecipeRepository, its use cases) won't need to
 * change when that swap happens.
 */
class RecipeRepositoryImpl : RecipeRepository {

    private val recipes = MutableStateFlow(sampleRecipes())

    override fun getRecipes(): Flow<List<Recipe>> = recipes.asStateFlow()

    override fun getRecipeById(
        id: String
    ): Flow<Recipe?> = recipes.map { list -> list.find { it.id == id } }

    override suspend fun toggleFavorite(
        id: String
    ) {
        recipes.update { list ->
            list.map { recipe ->
                if (recipe.id == id) {
                    recipe.copy(isFavorite = !recipe.isFavorite)
                } else {
                    recipe
                }
            }
        }
    }

}
