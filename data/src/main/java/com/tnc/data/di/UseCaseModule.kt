package com.tnc.data.di

import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
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
        ToggleFavoriteUseCase(
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

}
