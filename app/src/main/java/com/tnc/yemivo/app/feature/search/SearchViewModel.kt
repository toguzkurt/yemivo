package com.tnc.yemivo.app.feature.search

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class SearchViewModel(
    private val getRecipesUseCase: GetRecipesUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(initialState = SearchUiState()) {

    private val query = MutableStateFlow("")
    private val selectedCuisine = MutableStateFlow<String?>(null)

    init {
        observeResults()
    }

    fun onEvent(
        event: SearchUiEvent
    ) {
        when (event) {

            is SearchUiEvent.QueryChanged -> {
                query.value = event.query
            }

            is SearchUiEvent.CuisineFilterSelected -> {
                selectedCuisine.value = event.cuisine
            }

            is SearchUiEvent.RecipeClicked -> {
                sendEffect(SearchUiEffect.NavigateToRecipeDetail(event.recipeId))
            }

        }
    }

    private fun observeResults() {

        launch {

            combine(
                getRecipesUseCase(),
                query,
                selectedCuisine
            ) { recipes, query, cuisine ->
                SearchUiState(
                    query = query,
                    selectedCuisine = cuisine,
                    results = recipes.filter { recipe ->
                        (cuisine == null || recipe.cuisine == cuisine) &&
                            (query.isBlank() || recipe.name.contains(query, ignoreCase = true))
                    }
                )
            }.collect { newState ->

                setState { newState }

            }

        }

    }

}
