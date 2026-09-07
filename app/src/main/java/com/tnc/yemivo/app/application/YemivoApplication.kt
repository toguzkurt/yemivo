package com.tnc.yemivo.app.application

import android.app.Application
import com.tnc.data.di.databaseModule
import com.tnc.data.di.networkModule
import com.tnc.data.di.repositoryModule
import com.tnc.data.di.useCaseModule
import com.tnc.data.remote.mealdb.RecipeRemoteSeeder
import com.tnc.domain.notification.usecase.GenerateDailyRecipeNotificationUseCase
import com.tnc.yemivo.app.di.appModule
import com.tnc.yemivo.app.theme.NotificationPreferences
import com.tnc.yemivo.app.theme.ThemePreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YemivoApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val recipeSeeder: RecipeRemoteSeeder by inject()

    private val generateDailyRecipeNotificationUseCase: GenerateDailyRecipeNotificationUseCase by inject()

    private val themePreferences: ThemePreferences by inject()

    private val notificationPreferences: NotificationPreferences by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@YemivoApplication)
            modules(
                databaseModule,
                networkModule,
                repositoryModule,
                useCaseModule,
                appModule
            )
        }

        // Must happen before any Activity is created so the very first screen already renders
        // in the saved mode rather than flashing the default then switching.
        themePreferences.applySavedMode()

        applicationScope.launch {
            // One-time: populates the empty recipes table from TheMealDB on first launch. Later
            // launches see a non-empty table and RecipeRemoteSeeder.seedIfEmpty() no-ops.
            recipeSeeder.seedIfEmpty()

            // No-ops if today's daily-recipe notification already exists, or if the user has
            // turned this off in Settings.
            if (notificationPreferences.isDailyRecipeEnabled) {
                generateDailyRecipeNotificationUseCase()
            }
        }
    }
}
