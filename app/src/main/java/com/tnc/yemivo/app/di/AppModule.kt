package com.tnc.yemivo.app.di

import com.tnc.yemivo.app.feature.home.HomeViewModel
import com.tnc.yemivo.app.feature.recipedetail.RecipeDetailViewModel
import com.tnc.yemivo.app.feature.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::HomeViewModel)

    viewModelOf(::SearchViewModel)

    viewModel { (recipeId: String) ->
        RecipeDetailViewModel(
            recipeId = recipeId,
            getRecipeByIdUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }

}
