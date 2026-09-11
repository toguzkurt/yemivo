package com.tnc.yemivo.app.feature.cookmode

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase

class CookModeViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase
) : BaseViewModel<CookModeUiState, CookModeUiEffect>(
    initialState = CookModeUiState()
) {

    init {
        observeRecipe()
    }

    fun onEvent(
        event: CookModeUiEvent
    ) {
        when (event) {

            CookModeUiEvent.NextClicked -> moveStep(1)

            CookModeUiEvent.PrevClicked -> moveStep(-1)

        }
    }

    private fun observeRecipe() {

        launch {

            getRecipeByIdUseCase(recipeId).collect { fetched ->

                fetched?.let { recipe ->

                    setState {
                        copy(
                            recipe = recipe,
                            totalSteps = recipe.steps.size
                        )
                    }

                }

            }

        }

    }

    private fun moveStep(
        delta: Int
    ) {
        setState {
            copy(currentStepIndex = (currentStepIndex + delta).coerceIn(0, (totalSteps - 1).coerceAtLeast(0)))
        }
    }

}
