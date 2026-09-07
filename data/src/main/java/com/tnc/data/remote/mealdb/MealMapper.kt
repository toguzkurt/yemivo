package com.tnc.data.remote.mealdb

import com.tnc.data.local.recipe.RecipeEntity
import com.tnc.data.local.recipe.RecipeIngredientEntity

/**
 * TheMealDB has no prep/cook time, servings, or difficulty fields at all, so those stay null —
 * RecipeFormat.kt already falls back to the category name when they're missing.
 */
fun MealDto.toEntity(): RecipeEntity = RecipeEntity(
    id = idMeal,
    name = name.orEmpty(),
    cuisine = area.orEmpty().lowercase(),
    cuisineLabel = area.orEmpty(),
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
