package com.tnc.yemivo.app.feature.cuisinerecipes

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase

class CuisineRecipesViewModel(
    private val cuisine: String,
    cuisineLabel: String,
    private val getRecipesUseCase: GetRecipesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<CuisineRecipesUiState, CuisineRecipesUiEffect>(
    initialState = CuisineRecipesUiState(cuisineLabel = cuisineLabel)
) {

    init {
        observeRecipes()
    }

    fun onEvent(
        event: CuisineRecipesUiEvent
    ) {
        when (event) {

            is CuisineRecipesUiEvent.RecipeClicked -> {
                sendEffect(CuisineRecipesUiEffect.NavigateToRecipeDetail(event.recipeId))
            }

            is CuisineRecipesUiEvent.FavoriteClicked -> {
                launch { toggleFavoriteUseCase(event.recipeId) }
            }

        }
    }

    private fun observeRecipes() {

        launch {

            getRecipesUseCase().collect { recipes ->

                setState { copy(recipes = recipes.filter { it.cuisine == cuisine }) }

            }

        }

    }

}
