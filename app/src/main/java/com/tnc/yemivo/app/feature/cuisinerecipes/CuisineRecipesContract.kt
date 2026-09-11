package com.tnc.yemivo.app.feature.cuisinerecipes

import com.tnc.domain.recipe.model.Recipe

data class CuisineRecipesUiState(

    val cuisineLabel: String = "",

    val recipes: List<Recipe> = emptyList()

)

sealed interface CuisineRecipesUiEvent {

    data class RecipeClicked(
        val recipeId: String
    ) : CuisineRecipesUiEvent

    data class FavoriteClicked(
        val recipeId: String
    ) : CuisineRecipesUiEvent

}

sealed interface CuisineRecipesUiEffect {

    data class NavigateToRecipeDetail(
        val recipeId: String
    ) : CuisineRecipesUiEffect

}
