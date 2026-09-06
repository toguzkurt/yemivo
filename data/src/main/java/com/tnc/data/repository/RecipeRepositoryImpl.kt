package com.tnc.data.repository

import com.tnc.data.local.recipe.RecipeDao
import com.tnc.data.local.recipe.toDomain
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao
) : RecipeRepository {

    override fun getRecipes(): Flow<List<Recipe>> =
        recipeDao.getAll().map { entities -> entities.map { it.toDomain() } }

    override fun getRecipeById(
        id: String
    ): Flow<Recipe?> = recipeDao.getById(id).map { it?.toDomain() }

    override suspend fun toggleFavorite(
        id: String
    ) {
        val isCurrentlyFavorite = recipeDao.isFavorite(id) ?: false
        recipeDao.setFavorite(id, !isCurrentlyFavorite)
    }

}
