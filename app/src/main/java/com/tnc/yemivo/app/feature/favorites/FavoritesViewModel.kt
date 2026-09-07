package com.tnc.yemivo.app.feature.favorites

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase

class FavoritesViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<FavoritesUiState, FavoritesUiEffect>(initialState = FavoritesUiState()) {

    init {
        observeFavorites()
    }

    fun onEvent(
        event: FavoritesUiEvent
    ) {
        when (event) {

            is FavoritesUiEvent.RecipeClicked -> {
                sendEffect(FavoritesUiEffect.NavigateToRecipeDetail(event.recipeId))
            }

            is FavoritesUiEvent.FavoriteClicked -> {
                toggleFavorite(event.recipeId)
            }

            FavoritesUiEvent.ExploreClicked -> {
                sendEffect(FavoritesUiEffect.NavigateToHomeTab)
            }

        }
    }

    private fun observeFavorites() {

        launch {

            getRecipesUseCase().collect { recipes ->

                setState { copy(favorites = recipes.filter { it.isFavorite }) }

            }

        }

    }

    private fun toggleFavorite(
        id: String
    ) {
        launch {
            toggleFavoriteUseCase(id)
        }
    }

}
