package com.tnc.yemivo.app.feature.downloads

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase

class DownloadedRecipesViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<DownloadedRecipesUiState, DownloadedRecipesUiEffect>(
    initialState = DownloadedRecipesUiState()
) {

    init {
        observeDownloads()
    }

    fun onEvent(
        event: DownloadedRecipesUiEvent
    ) {
        when (event) {

            is DownloadedRecipesUiEvent.RecipeClicked -> {
                sendEffect(DownloadedRecipesUiEffect.NavigateToRecipeDetail(event.recipeId))
            }

            is DownloadedRecipesUiEvent.FavoriteClicked -> {
                launch { toggleFavoriteUseCase(event.recipeId) }
            }

            DownloadedRecipesUiEvent.ExploreClicked -> {
                sendEffect(DownloadedRecipesUiEffect.NavigateToHomeTab)
            }

        }
    }

    private fun observeDownloads() {

        launch {

            getRecipesUseCase().collect { recipes ->

                setState { copy(recipes = recipes.filter { it.isDownloaded }) }

            }

        }

    }

}
