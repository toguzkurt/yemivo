package com.tnc.yemivo.app.feature.recipedetail

import com.tnc.core.base.BaseViewModel
import com.tnc.core.common.result.UiText
import com.tnc.domain.notification.usecase.NotifyDownloadCompleteUseCase
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.recipe.usecase.ToggleDownloadUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase
import com.tnc.domain.shoppinglist.usecase.AddRecipeToShoppingListUseCase
import com.tnc.yemivo.R

class RecipeDetailViewModel(
    private val recipeId: String,
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleDownloadUseCase: ToggleDownloadUseCase,
    private val addRecipeToShoppingListUseCase: AddRecipeToShoppingListUseCase,
    private val notifyDownloadCompleteUseCase: NotifyDownloadCompleteUseCase
) : BaseViewModel<RecipeDetailUiState, RecipeDetailUiEffect>(initialState = RecipeDetailUiState()) {

    init {
        observeRecipe()
    }

    fun onEvent(
        event: RecipeDetailUiEvent
    ) {
        when (event) {

            RecipeDetailUiEvent.FavoriteClicked -> {
                toggleFavorite()
            }

            RecipeDetailUiEvent.AddToShoppingListClicked -> {
                addToShoppingList()
            }

            RecipeDetailUiEvent.DownloadClicked -> {
                toggleDownload()
            }

            is RecipeDetailUiEvent.TabSelected -> {
                setState { copy(selectedTab = event.tab) }
            }

        }
    }

    private fun observeRecipe() {

        launch {

            getRecipeByIdUseCase(recipeId).collect { recipe ->

                setState { copy(recipe = recipe) }

            }

        }

    }

    private fun toggleFavorite() {
        launch {
            toggleFavoriteUseCase(recipeId)
        }
    }

    private fun toggleDownload() {

        val recipe = state.value.recipe ?: return

        launch {

            val isNowDownloaded = toggleDownloadUseCase(recipeId)

            if (isNowDownloaded) {

                notifyDownloadCompleteUseCase(recipe)

                sendEffect(
                    RecipeDetailUiEffect.ShowMessage(
                        UiText.StringResource(R.string.recipe_download_added)
                    )
                )

            } else {

                sendEffect(
                    RecipeDetailUiEffect.ShowMessage(
                        UiText.StringResource(R.string.recipe_download_removed)
                    )
                )

            }

        }

    }

    private fun addToShoppingList() {

        val recipe = state.value.recipe ?: return

        launch {

            addRecipeToShoppingListUseCase(recipe)

            sendEffect(
                RecipeDetailUiEffect.ShowMessage(
                    UiText.StringResource(R.string.shopping_list_item_added)
                )
            )

        }

    }

}
