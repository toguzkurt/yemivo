package com.tnc.data.di

import com.tnc.data.repository.RecipeRepositoryImpl
import com.tnc.domain.recipe.repository.RecipeRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<RecipeRepository> {
        RecipeRepositoryImpl(
            recipeDao = get()
        )
    }

}
