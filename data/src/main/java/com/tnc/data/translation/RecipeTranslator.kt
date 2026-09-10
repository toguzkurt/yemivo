package com.tnc.data.translation

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.tnc.data.local.recipe.RecipeDao
import kotlinx.coroutines.tasks.await

/**
 * One-time, on-device EN->TR backfill for recipe content pulled from TheMealDB (English-only).
 * Runs once per row: a row's nameTr stays null until translated, so a later launch only retries
 * rows a previous pass missed (matches RecipeRemoteSeeder's per-item fault tolerance).
 */
class RecipeTranslator(
    private val recipeDao: RecipeDao
) {

    suspend fun translateIfNeeded() {

        val pending = recipeDao.getUntranslated()
        if (pending.isEmpty()) return

        val translator = Translation.getClient(
            TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.TURKISH)
                .build()
        )

        try {

            translator.downloadModelIfNeeded().await()

            pending.forEach { recipe ->
                runCatching {
                    val nameTr = translator.translate(recipe.name).await()
                    val stepsTr = recipe.steps.map { translator.translate(it).await() }
                    val ingredientsTr = recipe.ingredients.map {
                        it.copy(nameTr = translator.translate(it.name).await())
                    }
                    recipeDao.updateTranslation(recipe.id, nameTr, stepsTr, ingredientsTr)
                }
            }

        } finally {
            translator.close()
        }

    }

}
