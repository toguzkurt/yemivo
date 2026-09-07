package com.tnc.yemivo.app.di

import com.tnc.yemivo.app.feature.downloads.DownloadedRecipesViewModel
import com.tnc.yemivo.app.feature.favorites.FavoritesViewModel
import com.tnc.yemivo.app.feature.home.HomeViewModel
import com.tnc.yemivo.app.feature.notifications.NotificationsViewModel
import com.tnc.yemivo.app.feature.recipedetail.RecipeDetailViewModel
import com.tnc.yemivo.app.feature.search.SearchViewModel
import com.tnc.yemivo.app.feature.shoppinglist.ShoppingListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::HomeViewModel)

    viewModelOf(::SearchViewModel)

    viewModelOf(::FavoritesViewModel)

    viewModelOf(::ShoppingListViewModel)

    viewModelOf(::DownloadedRecipesViewModel)

    viewModelOf(::NotificationsViewModel)

    viewModel { (recipeId: String) ->
        RecipeDetailViewModel(
            recipeId = recipeId,
            getRecipeByIdUseCase = get(),
            toggleFavoriteUseCase = get(),
            toggleDownloadUseCase = get(),
            addRecipeToShoppingListUseCase = get(),
            notifyDownloadCompleteUseCase = get()
        )
    }

}
