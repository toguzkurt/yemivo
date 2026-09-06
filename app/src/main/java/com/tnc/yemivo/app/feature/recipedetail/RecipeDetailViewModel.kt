package com.tnc.yemivo.app.feature.recipedetail

import com.tnc.core.base.BaseViewModel
import com.tnc.core.common.result.UiText
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase

class RecipeDetailViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<RecipeDetailUiState, RecipeDetailUiEffect>(initialState = RecipeDetailUiState()) {

    init {
        observeRecipe()
    }

    fun onEvent(
        event: RecipeDetailUiEvent
    ) {
        when (event) {

            RecipeDetailUiEvent.FavoriteClicked -> {
                toggleFavorite()
            }

            RecipeDetailUiEvent.AddToShoppingListClicked -> {
                sendEffect(
                    RecipeDetailUiEffect.ShowMessage(
                        UiText.DynamicString("Alışveriş listesine eklendi")
                    )
                )
            }

        }
    }

    private fun observeRecipe() {

        launch {

            getRecipeByIdUseCase(recipeId).collect { recipe ->

                setState { copy(recipe = recipe) }

            }

        }

    }

    private fun toggleFavorite() {
        launch {
            toggleFavoriteUseCase(recipeId)
        }
    }

}
