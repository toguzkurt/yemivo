package com.tnc.data.di

import com.tnc.domain.recipe.usecase.GetRecipeByIdUseCase
import com.tnc.domain.recipe.usecase.GetRecipesUseCase
import com.tnc.domain.recipe.usecase.ToggleFavoriteUseCase
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

}
