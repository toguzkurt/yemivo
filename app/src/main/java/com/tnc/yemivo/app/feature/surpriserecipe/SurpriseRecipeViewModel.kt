package com.tnc.yemivo.app.feature.surpriserecipe

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRandomRecipeUseCase

class SurpriseRecipeViewModel(
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase
) : BaseViewModel<SurpriseRecipeUiState, SurpriseRecipeUiEffect>(
    initialState = SurpriseRecipeUiState()
) {

    init {
        reroll()
    }

    fun onEvent(
        event: SurpriseRecipeUiEvent
    ) {
        when (event) {

            is SurpriseRecipeUiEvent.RerollClicked -> reroll()

            is SurpriseRecipeUiEvent.ViewRecipeClicked -> {
                state.value.recipe?.let { recipe ->
                    sendEffect(SurpriseRecipeUiEffect.NavigateToRecipeDetail(recipe.id))
                }
            }

        }
    }

    private fun reroll() {
        launch {
            val recipe = getRandomRecipeUseCase()
            setState { copy(recipe = recipe) }
        }
    }

}
