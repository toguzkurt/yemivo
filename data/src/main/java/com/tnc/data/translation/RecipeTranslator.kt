package com.tnc.data.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.tnc.data.local.recipe.RecipeDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * One-time, on-device EN->TR backfill for recipe content pulled from TheMealDB (English-only).
 * Runs once per row: a row's nameTr stays null until translated, so a later launch only retries
 * rows a previous pass missed (matches RecipeRemoteSeeder's per-item fault tolerance).
 */
class RecipeTranslator(
    private val recipeDao: RecipeDao
) {

    // Translating the whole catalog can take much longer than seeding — SplashViewModel fires
    // this and moves on rather than blocking the launch on it, so it needs a scope that outlives
    // whatever screen triggered it. Owned here (lives as long as this singleton, i.e. the
    // process) rather than handed a caller's viewModelScope that would cancel it mid-run.
    private val backgroundScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Fire-and-forget: screens showing recipe content already fall back to English until a
     * row's nameTr lands, so there's nothing for callers to await here.
     */
    fun translateInBackground() {
        backgroundScope.launch {
            runCatching { translateIfNeeded() }
        }
    }

    suspend fun translateIfNeeded(
        onProgress: suspend (done: Int, total: Int) -> Unit = { _, _ -> }
    ) {

        val pending = recipeDao.getUntranslated()
        if (pending.isEmpty()) {
            onProgress(1, 1)
            return
        }

        val translator = Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.TURKISH)
                .build()
        )

        try {

            translator.downloadModelIfNeeded().await()

            pending.forEachIndexed { index, recipe ->
                runCatching {
                    val nameTr = translator.translate(recipe.name).await()
                    val stepsTr = recipe.steps.map { translator.translate(it).await() }
                    val ingredientsTr = recipe.ingredients.map {
                        it.copy(nameTr = translator.translate(it.name).await())
                    }
                    recipeDao.updateTranslation(recipe.id, nameTr, stepsTr, ingredientsTr)
                }
                onProgress(index + 1, pending.size)
            }

        } finally {
            translator.close()
        }

    }

}
