package com.tnc.yemivo.app.feature.downloads

import com.tnc.domain.recipe.model.Recipe

data class DownloadedRecipesUiState(

    val recipes: List<Recipe> = emptyList()

)

sealed interface DownloadedRecipesUiEvent {

    data class RecipeClicked(
        val recipeId: String
    ) : DownloadedRecipesUiEvent

    data class FavoriteClicked(
        val recipeId: String
    ) : DownloadedRecipesUiEvent

    data object ExploreClicked : DownloadedRecipesUiEvent

}

sealed interface DownloadedRecipesUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : DownloadedRecipesUiEffect

    data object NavigateToHomeTab : DownloadedRecipesUiEffect

}
