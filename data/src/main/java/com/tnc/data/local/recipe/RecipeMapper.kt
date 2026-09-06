package com.tnc.data.local.recipe

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.RecipeIngredient

fun RecipeEntity.toDomain(): Recipe = Recipe(
    id = id,
    name = name,
    cuisine = cuisine,
    cuisineLabel = cuisineLabel,
    category = category,
    prepMinutes = prepMinutes,
    cookMinutes = cookMinutes,
    servings = servings,
    difficulty = difficulty,
    ingredients = ingredients.map { RecipeIngredient(it.name, it.amount, it.unit) },
    steps = steps,
    isFavorite = isFavorite
)
