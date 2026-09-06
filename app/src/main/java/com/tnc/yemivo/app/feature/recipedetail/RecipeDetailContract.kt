package com.tnc.yemivo.app.feature.recipedetail

import com.tnc.core.common.result.UiText
import com.tnc.domain.recipe.model.Recipe

data class RecipeDetailUiState(

    val recipe: Recipe? = null

)

sealed interface RecipeDetailUiEvent {

    data object FavoriteClicked : RecipeDetailUiEvent

    data object AddToShoppingListClicked : RecipeDetailUiEvent

}

sealed interface RecipeDetailUiEffect {

    data class ShowMessage(
        val message: UiText
    ) : RecipeDetailUiEffect

}
