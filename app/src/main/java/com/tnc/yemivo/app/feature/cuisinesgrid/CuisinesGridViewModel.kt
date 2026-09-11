package com.tnc.yemivo.app.feature.cuisinesgrid

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.yemivo.app.feature.common.availableCuisines

class CuisinesGridViewModel(
    private val getRecipesUseCase: GetRecipesUseCase
) : BaseViewModel<CuisinesGridUiState, CuisinesGridUiEffect>(initialState = CuisinesGridUiState()) {

    init {
        observeCuisines()
    }

    fun onEvent(
        event: CuisinesGridUiEvent
    ) {
        when (event) {

            is CuisinesGridUiEvent.CuisineClicked -> {
                sendEffect(
                    CuisinesGridUiEffect.NavigateToCuisineRecipes(event.cuisine, event.cuisineLabel)
                )
            }

        }
    }

    private fun observeCuisines() {

        launch {

            getRecipesUseCase().collect { recipes ->

                // No minCount floor here, unlike the Home/Search filter chips — this is a full
                // directory, so even a single-recipe area still gets its own cell.
                setState { copy(cuisines = recipes.availableCuisines(minCount = 1)) }

            }

        }

    }

}
