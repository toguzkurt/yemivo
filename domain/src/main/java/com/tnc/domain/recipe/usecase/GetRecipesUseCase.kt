package com.tnc.domain.recipe.usecase

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

// Not a BaseUseCase: that class models a single suspend request/response wrapped in Result,
// which doesn't fit an ongoing reactive stream of recipe updates (favorites toggling etc.).
class GetRecipesUseCase(
    private val repository: RecipeRepository
) {

    operator fun invoke(): Flow<List<Recipe>> = repository.getRecipes()

}
