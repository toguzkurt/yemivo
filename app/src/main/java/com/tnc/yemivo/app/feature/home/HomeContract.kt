package com.tnc.yemivo.app.feature.home

import com.tnc.domain.recipe.model.Recipe

data class HomeUiState(

    val recipes: List<Recipe> = emptyList(),

    val selectedCuisine: String? = null

)

sealed interface HomeUiEvent {

    data class CuisineFilterSelected(
        val cuisine: String?
    ) : HomeUiEvent

    data class RecipeClicked(
        val recipeId: String
    ) : HomeUiEvent

    data class FavoriteClicked(
        val recipeId: String
    ) : HomeUiEvent

}

sealed interface HomeUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : HomeUiEffect

}
