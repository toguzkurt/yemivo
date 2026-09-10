package com.tnc.yemivo.app.feature.common

import com.tnc.domain.recipe.model.Recipe

data class CuisineOption(
    val cuisine: String,
    val cuisineLabel: String,
    val count: Int
)

/**
 * Cuisines with too few recipes (TheMealDB's community-submitted catalog has plenty of
 * one-off areas) would just clutter a filter chip row without being useful — only cuisines
 * with at least [minCount] recipes are offered as a filter, most-populous first.
 */
fun List<Recipe>.availableCuisines(
    minCount: Int = 3
): List<CuisineOption> =
    filter { it.cuisine.isNotBlank() }
        .groupBy { it.cuisine }
        .mapNotNull { (cuisine, recipes) ->
            recipes.takeIf { it.size >= minCount }
                ?.let { CuisineOption(cuisine, it.first().cuisineLabel, it.size) }
        }
        .sortedByDescending { it.count }
