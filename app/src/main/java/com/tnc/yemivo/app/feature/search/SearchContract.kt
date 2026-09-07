package com.tnc.yemivo.app.feature.search

import com.tnc.domain.recipe.model.Recipe

data class SearchUiState(

    val query: String = "",

    val selectedCuisine: String? = null,

    val results: List<Recipe> = emptyList()

)

sealed interface SearchUiEvent {

    data class QueryChanged(
        val query: String
    ) : SearchUiEvent

    data class CuisineFilterSelected(
        val cuisine: String?
    ) : SearchUiEvent

    data class RecipeClicked(
        val recipeId: String
    ) : SearchUiEvent

}

sealed interface SearchUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : SearchUiEffect

}
