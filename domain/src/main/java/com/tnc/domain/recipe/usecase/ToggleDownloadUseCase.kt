package com.tnc.domain.recipe.usecase

import com.tnc.domain.recipe.repository.RecipeRepository

class ToggleDownloadUseCase(
    private val repository: RecipeRepository
) {

    suspend operator fun invoke(
        id: String
    ): Boolean = repository.toggleDownload(id)

}
