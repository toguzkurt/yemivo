package com.tnc.data.remote.mealdb

import com.tnc.data.local.recipe.RecipeEntity
import com.tnc.data.local.recipe.RecipeIngredientEntity

/**
 * A handful of TheMealDB's community-submitted meals put the country name in strArea instead of
 * the site's own canonical adjective (e.g. "India" instead of "Indian") — normalize the ones
 * we've actually seen so cuisine-filter chips match them too.
 */
private val CUISINE_AREA_ALIASES = mapOf(
    "india" to "Indian",
    "united states" to "American"
)

private fun canonicalArea(area: String?): String {
    val trimmed = area.orEmpty().trim()
    return CUISINE_AREA_ALIASES[trimmed.lowercase()] ?: trimmed
}

/**
 * TheMealDB has no prep/cook time, servings, or difficulty fields at all, so those stay null —
 * RecipeFormat.kt already falls back to the category name when they're missing.
 */
fun MealDto.toEntity(): RecipeEntity = RecipeEntity(
    id = idMeal,
    name = name.orEmpty(),
    cuisine = canonicalArea(area).lowercase(),
    cuisineLabel = canonicalArea(area),
    category = category.orEmpty(),
    prepMinutes = null,
    cookMinutes = null,
    servings = null,
    difficulty = null,
    ingredients = ingredientMeasurePairs().map { (name, measure) ->
        RecipeIngredientEntity(name = name, amount = measure, unit = "")
    },
    steps = instructions
        .orEmpty()
        .split("\r\n", "\n")
        .map { it.trim() }
        // Drop blank lines and bare "STEP 1" headers — our own adapter already numbers steps.
        .filter { it.isNotBlank() && !it.matches(Regex("(?i)step\\s*\\d+\\.?")) },
    isFavorite = false,
    imageUrl = thumbnail
)
