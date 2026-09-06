package com.tnc.yemivo.app.feature.home

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class HomeViewModel(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<HomeUiState, HomeUiEffect>(initialState = HomeUiState()) {

    private val selectedCuisine = MutableStateFlow<String?>(null)

    init {
        observeRecipes()
    }

    fun onEvent(
        event: HomeUiEvent
    ) {
        when (event) {

            is HomeUiEvent.CuisineFilterSelected -> {
                selectedCuisine.value = event.cuisine
            }

            is HomeUiEvent.RecipeClicked -> {
                sendEffect(HomeUiEffect.NavigateToRecipeDetail(event.recipeId))
            }

            is HomeUiEvent.FavoriteClicked -> {
                toggleFavorite(event.recipeId)
            }

        }
    }

    private fun observeRecipes() {

        launch {

            combine(
                getRecipesUseCase(),
                selectedCuisine
            ) { recipes, cuisine ->
                HomeUiState(
                    recipes = recipes.filter { cuisine == null || it.cuisine == cuisine },
                    selectedCuisine = cuisine
                )
            }.collect { newState ->

                setState { newState }

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
