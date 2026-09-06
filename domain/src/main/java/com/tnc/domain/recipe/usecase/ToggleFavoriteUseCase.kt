package com.tnc.domain.recipe.usecase

import com.tnc.domain.recipe.repository.RecipeRepository

class ToggleFavoriteUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(
        id: String
    ) {
        repository.toggleFavorite(id)
    }

}
