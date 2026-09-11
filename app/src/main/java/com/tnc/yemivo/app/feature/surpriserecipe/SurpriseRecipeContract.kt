package com.tnc.yemivo.app.feature.surpriserecipe

import com.tnc.domain.recipe.model.Recipe

data class SurpriseRecipeUiState(

    val recipe: Recipe? = null

)

sealed interface SurpriseRecipeUiEvent {

    data object RerollClicked : SurpriseRecipeUiEvent

    data object ViewRecipeClicked : SurpriseRecipeUiEvent

}

sealed interface SurpriseRecipeUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : SurpriseRecipeUiEffect

}
