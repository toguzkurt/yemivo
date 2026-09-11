package com.tnc.yemivo.app.di

import com.tnc.yemivo.app.feature.cuisinerecipes.CuisineRecipesViewModel
import com.tnc.yemivo.app.feature.cuisinesgrid.CuisinesGridViewModel
import com.tnc.yemivo.app.feature.downloads.DownloadedRecipesViewModel
import com.tnc.yemivo.app.feature.favorites.FavoritesViewModel
import com.tnc.yemivo.app.feature.home.HomeViewModel
import com.tnc.yemivo.app.feature.notifications.NotificationsViewModel
import com.tnc.yemivo.app.feature.recipedetail.RecipeDetailViewModel
import com.tnc.yemivo.app.feature.search.SearchViewModel
import com.tnc.yemivo.app.feature.shoppinglist.ShoppingListViewModel
import com.tnc.yemivo.app.feature.splash.SplashViewModel
import com.tnc.yemivo.app.feature.surpriserecipe.SurpriseRecipeViewModel
import com.tnc.yemivo.app.onboarding.OnboardingPreferences
import com.tnc.yemivo.app.session.SessionPreferences
import com.tnc.yemivo.app.theme.NotificationPreferences
import com.tnc.yemivo.app.theme.ThemePreferences
import com.tnc.yemivo.app.theme.UnitPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    single { SessionPreferences(androidContext()) }

    single { ThemePreferences(androidContext()) }

    single { UnitPreferences(androidContext()) }

    single { NotificationPreferences(androidContext()) }

    single { OnboardingPreferences(androidContext()) }

    viewModelOf(::SplashViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::SearchViewModel)

    viewModelOf(::FavoritesViewModel)

    viewModelOf(::ShoppingListViewModel)

    viewModelOf(::DownloadedRecipesViewModel)

    viewModelOf(::NotificationsViewModel)

    viewModelOf(::CuisinesGridViewModel)

    viewModelOf(::SurpriseRecipeViewModel)

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

    viewModel { (cuisine: String, cuisineLabel: String) ->
        CuisineRecipesViewModel(
            cuisine = cuisine,
            cuisineLabel = cuisineLabel,
            getRecipesUseCase = get(),
            toggleFavoriteUseCase = get()
        )
    }

}
