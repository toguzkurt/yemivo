package com.tnc.data.local.recipe

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.roundToInt

/**
 * Populates the (empty, first-launch) recipes table from the bundled JSON assets instead of a
 * live API — see recipes_turkish/italian/mexican/japanese.json for provenance. Runs once: later
 * launches see a non-empty table and skip straight past [seedIfEmpty].
 */
class RecipeSeeder(
    private val context: Context,
    private val recipeDao: RecipeDao
) {

    private val gson = Gson()

    suspend fun seedIfEmpty() {

        if (recipeDao.count() > 0) return

        val entities = CUISINE_ASSETS.flatMap { (assetFileName, cuisine, cuisineLabel) ->
            loadCuisine(assetFileName, cuisine, cuisineLabel)
        }

        recipeDao.insertAll(entities)

    }

    private fun loadCuisine(
        assetFileName: String,
        cuisine: String,
        cuisineLabel: String
    ): List<RecipeEntity> {

        val json = context.assets.open(assetFileName)
            .bufferedReader(Charsets.UTF_8)
            .use { it.readText() }

        val type = object : TypeToken<List<RecipeAssetDto>>() {}.type
        val dtos: List<RecipeAssetDto> = gson.fromJson(json, type)

        return dtos.mapIndexed { index, dto ->
            RecipeEntity(
                id = "$cuisine-$index",
                name = dto.name,
                cuisine = cuisine,
                cuisineLabel = cuisineLabel,
                category = dto.category,
                prepMinutes = dto.prepMinutes?.roundToInt(),
                cookMinutes = dto.cookMinutes?.roundToInt(),
                servings = dto.servings?.roundToInt(),
                difficulty = dto.difficulty,
                ingredients = dto.ingredients.map {
                    RecipeIngredientEntity(it.name, it.amount.orEmpty(), it.unit.orEmpty())
                },
                steps = dto.steps,
                isFavorite = false
            )
        }

    }

    private data class CuisineAsset(
        val assetFileName: String,
        val cuisine: String,
        val cuisineLabel: String
    )

    private companion object {
        val CUISINE_ASSETS = listOf(
            CuisineAsset("recipes_turkish.json", "turkish", "Türk mutfağı"),
            CuisineAsset("recipes_italian.json", "italian", "İtalyan mutfağı"),
            CuisineAsset("recipes_mexican.json", "mexican", "Meksika mutfağı"),
            CuisineAsset("recipes_japanese.json", "japanese", "Japon mutfağı")
        )
    }

}
