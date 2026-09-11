package com.tnc.yemivo.app.feature.portionadjuster

import com.tnc.core.base.BaseViewModel
import com.tnc.core.common.result.UiText
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.shoppinglist.usecase.AddRecipeToShoppingListUseCase
import com.tnc.yemivo.R

class PortionAdjusterViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val addRecipeToShoppingListUseCase: AddRecipeToShoppingListUseCase
) : BaseViewModel<PortionAdjusterUiState, PortionAdjusterUiEffect>(
    initialState = PortionAdjusterUiState()
) {

    init {
        observeRecipe()
    }

    fun onEvent(
        event: PortionAdjusterUiEvent
    ) {
        when (event) {

            PortionAdjusterUiEvent.IncrementClicked -> adjustServings(1)

            PortionAdjusterUiEvent.DecrementClicked -> adjustServings(-1)

            PortionAdjusterUiEvent.AddToShoppingListClicked -> addToShoppingList()

        }
    }

    private fun observeRecipe() {

        launch {

            getRecipeByIdUseCase(recipeId).collect { fetched ->

                fetched?.let { recipe ->

                    setState {
                        copy(
                            recipe = recipe,
                            currentServings = if (this.recipe == null) {
                                recipe.servings ?: DEFAULT_SERVINGS
                            } else {
                                currentServings
                            }
                        )
                    }

                }

            }

        }

    }

    private fun adjustServings(
        delta: Int
    ) {
        setState { copy(currentServings = (currentServings + delta).coerceIn(MIN_SERVINGS, MAX_SERVINGS)) }
    }

    private fun addToShoppingList() {

        val recipe = state.value.recipe ?: return
        val originalServings = recipe.servings ?: DEFAULT_SERVINGS
        val targetServings = state.value.currentServings

        launch {

            val scaledRecipe = recipe.copy(
                ingredients = recipe.ingredients.map {
                    it.scaledForShoppingList(originalServings, targetServings)
                }
            )

            addRecipeToShoppingListUseCase(scaledRecipe)

            sendEffect(
                PortionAdjusterUiEffect.ShowMessage(
                    UiText.StringResource(R.string.shopping_list_item_added)
                )
            )

        }

    }

    private companion object {
        const val MIN_SERVINGS = 1
        const val MAX_SERVINGS = 50
    }

}
