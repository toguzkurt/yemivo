package com.tnc.yemivo.app.feature.recipedetail

import com.tnc.core.common.result.UiText
import com.tnc.domain.recipe.model.Recipe

enum class RecipeDetailTab {
    INGREDIENTS,
    INSTRUCTIONS
}

data class RecipeDetailUiState(

    val recipe: Recipe? = null,

    val selectedTab: RecipeDetailTab = RecipeDetailTab.INSTRUCTIONS

)

sealed interface RecipeDetailUiEvent {

    data object FavoriteClicked : RecipeDetailUiEvent

    data object AddToShoppingListClicked : RecipeDetailUiEvent

    data class TabSelected(
        val tab: RecipeDetailTab
    ) : RecipeDetailUiEvent

}

sealed interface RecipeDetailUiEffect {

    data class ShowMessage(
        val message: UiText
    ) : RecipeDetailUiEffect

}
