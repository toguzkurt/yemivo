package com.tnc.yemivo.app.feature.portionadjuster

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase

class PortionAdjusterViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
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

    private companion object {
        const val MIN_SERVINGS = 1
        const val MAX_SERVINGS = 50
    }

}
