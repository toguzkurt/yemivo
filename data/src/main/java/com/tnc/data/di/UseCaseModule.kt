package com.tnc.data.di

import com.tnc.domain.notification.usecase.GenerateDailyRecipeNotificationUseCase
import com.tnc.domain.notification.usecase.GetNotificationsUseCase
import com.tnc.domain.notification.usecase.MarkAllNotificationsReadUseCase
import com.tnc.domain.notification.usecase.NotifyDownloadCompleteUseCase
import com.tnc.domain.recipe.usecase.GetRandomRecipeUseCase
import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleDownloadUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase
import com.tnc.domain.shoppinglist.usecase.AddRecipeToShoppingListUseCase
import com.tnc.domain.shoppinglist.usecase.ClearShoppingListUseCase
import com.tnc.domain.shoppinglist.usecase.GetShoppingListUseCase
import com.tnc.domain.shoppinglist.usecase.ToggleShoppingListItemUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetRecipesUseCase(
            repository = get()
        )
    }

    factory {
        GetRecipeByIdUseCase(
            repository = get()
        )
    }

    factory {
        GetRandomRecipeUseCase(
            repository = get()
        )
    }

    factory {
        ToggleFavoriteUseCase(
            repository = get()
        )
    }

    factory {
        ToggleDownloadUseCase(
            repository = get()
        )
    }

    factory {
        GetShoppingListUseCase(
            repository = get()
        )
    }

    factory {
        AddRecipeToShoppingListUseCase(
            repository = get()
        )
    }

    factory {
        ToggleShoppingListItemUseCase(
            repository = get()
        )
    }

    factory {
        ClearShoppingListUseCase(
            repository = get()
        )
    }

    factory {
        GetNotificationsUseCase(
            repository = get()
        )
    }

    factory {
        NotifyDownloadCompleteUseCase(
            repository = get()
        )
    }

    factory {
        GenerateDailyRecipeNotificationUseCase(
            repository = get()
        )
    }

    factory {
        MarkAllNotificationsReadUseCase(
            repository = get()
        )
    }

}
