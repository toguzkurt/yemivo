package com.tnc.yemivo.app.feature.favorites

import com.tnc.domain.recipe.model.Recipe

data class FavoritesUiState(

    val favorites: List<Recipe> = emptyList()

)

sealed interface FavoritesUiEvent {

    data class RecipeClicked(
        val recipeId: String
    ) : FavoritesUiEvent

    data class FavoriteClicked(
        val recipeId: String
    ) : FavoritesUiEvent

    data object ExploreClicked : FavoritesUiEvent

}

sealed interface FavoritesUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : FavoritesUiEffect

    data object NavigateToHomeTab : FavoritesUiEffect

}
