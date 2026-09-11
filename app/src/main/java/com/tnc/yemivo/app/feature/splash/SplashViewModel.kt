package com.tnc.yemivo.app.feature.splash

import com.tnc.core.base.BaseViewModel
import com.tnc.data.remote.mealdb.RecipeRemoteSeeder
import com.tnc.data.translation.RecipeTranslator
import com.tnc.domain.notification.usecase.GenerateDailyRecipeNotificationUseCase
import com.tnc.yemivo.app.theme.NotificationPreferences
import kotlinx.coroutines.delay

class SplashViewModel(
    private val recipeSeeder: RecipeRemoteSeeder,
    private val recipeTranslator: RecipeTranslator,
    private val generateDailyRecipeNotificationUseCase: GenerateDailyRecipeNotificationUseCase,
    private val notificationPreferences: NotificationPreferences
) : BaseViewModel<SplashUiState, SplashUiEffect>(initialState = SplashUiState()) {

    init {
        launch { runStartup() }
    }

    private suspend fun runStartup() {

        val startedAt = System.currentTimeMillis()

        recipeSeeder.seedIfEmpty { done, total ->
            setState { copy(progress = done.toFloat() / total) }
        }

        // Translating the full catalog takes far longer than seeding — it keeps running in the
        // background instead of holding up the launch; recipe screens already fall back to
        // English until a row's nameTr lands.
        recipeTranslator.translateInBackground()

        setState { copy(progress = 1f) }

        // No-ops if today's daily-recipe notification already exists, or if the user has
        // turned this off in Settings.
        if (notificationPreferences.isDailyRecipeEnabled) {
            generateDailyRecipeNotificationUseCase()
        }

        // Real work finishes in well under a second on any launch after the first (seedIfEmpty
        // no-ops instantly) — without a floor, the splash would just flash and look broken
        // instead of reading as an intentional screen.
        val elapsed = System.currentTimeMillis() - startedAt
        if (elapsed < MIN_DURATION_MS) {
            delay(MIN_DURATION_MS - elapsed)
        }

        sendEffect(SplashUiEffect.NavigateToMain)

    }

    private companion object {
        const val MIN_DURATION_MS = 900L
    }

}
