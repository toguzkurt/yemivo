package com.tnc.domain.recipe.usecase

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.repository.RecipeRepository

class GetRandomRecipeUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(): Recipe? = repository.getRandomRecipe()

}
