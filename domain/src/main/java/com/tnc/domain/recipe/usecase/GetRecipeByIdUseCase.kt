package com.tnc.domain.recipe.usecase

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow

class GetRecipeByIdUseCase(
    private val repository: RecipeRepository
) {

    operator fun invoke(
        id: String
    ): Flow<Recipe?> = repository.getRecipeById(id)

}
