package com.tnc.data.remote.mealdb

import com.tnc.data.local.recipe.RecipeDao

/**
 * TheMealDB has no "list everything" endpoint, but search.php?f={letter} returns full meal
 * detail (not just names) for every meal starting with that letter — looping a-z is their own
 * documented way to pull the whole free-tier catalog. Runs once: a later launch sees a
 * non-empty table and no-ops. Each letter is fetched independently so a handful of failures
 * (e.g. flaky network) still leave the rest of the catalog seeded rather than aborting entirely.
 */
class RecipeRemoteSeeder(
    private val api: MealDbApi,
    private val recipeDao: RecipeDao
) {

    suspend fun seedIfEmpty() {

        if (recipeDao.count() > 0) return

        val entities = ('a'..'z')
            .mapNotNull { letter ->
                runCatching { api.searchByFirstLetter(letter.toString()).meals }.getOrNull()
            }
            .flatten()
            .distinctBy { it.idMeal }
            .map { it.toEntity() }

        if (entities.isNotEmpty()) {
            recipeDao.insertAll(entities)
        }

    }

}
