package com.tnc.data.di

import com.tnc.data.repository.NotificationRepositoryImpl
import com.tnc.data.repository.RecipeRepositoryImpl
import com.tnc.data.repository.ShoppingListRepositoryImpl
import com.tnc.domain.notification.repository.NotificationRepository
import com.tnc.domain.recipe.repository.RecipeRepository
import com.tnc.domain.shoppinglist.repository.ShoppingListRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<RecipeRepository> {
        RecipeRepositoryImpl(
            recipeDao = get()
        )
    }

    single<ShoppingListRepository> {
        ShoppingListRepositoryImpl(
            shoppingListDao = get()
        )
    }

    single<NotificationRepository> {
        NotificationRepositoryImpl(
            notificationDao = get(),
            recipeDao = get()
        )
    }

}
