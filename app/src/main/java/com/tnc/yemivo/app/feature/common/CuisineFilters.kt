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

/**
 * Flag emoji for the Cuisines Grid cells, keyed by the same English cuisineLabel used
 * throughout (see CUISINE_LABEL_TR in RecipeFormat.kt) — falls back to a generic globe for
 * any area TheMealDB adds that isn't mapped here yet, same "never crash on an unmapped
 * cuisine" spirit as cuisineLabelTr().
 */
private val CUISINE_FLAG = mapOf(
    "Algerian" to "🇩🇿",
    "American" to "🇺🇸",
    "Argentina" to "🇦🇷",
    "Australian" to "🇦🇺",
    "British" to "🇬🇧",
    "Canadian" to "🇨🇦",
    "Chinese" to "🇨🇳",
    "Croatian" to "🇭🇷",
    "Egyptian" to "🇪🇬",
    "Filipino" to "🇵🇭",
    "France" to "🇫🇷",
    "French" to "🇫🇷",
    "Greek" to "🇬🇷",
    "Indian" to "🇮🇳",
    "Irish" to "🇮🇪",
    "Italian" to "🇮🇹",
    "Jamaican" to "🇯🇲",
    "Japanese" to "🇯🇵",
    "Kenyan" to "🇰🇪",
    "Malaysian" to "🇲🇾",
    "Mexican" to "🇲🇽",
    "Moroccan" to "🇲🇦",
    "Netherlands" to "🇳🇱",
    "Dutch" to "🇳🇱",
    "Norway" to "🇳🇴",
    "Polish" to "🇵🇱",
    "Portuguese" to "🇵🇹",
    "Russian" to "🇷🇺",
    "Saudi Arabian" to "🇸🇦",
    "Slovakia" to "🇸🇰",
    "Spanish" to "🇪🇸",
    "Syrian" to "🇸🇾",
    "Thai" to "🇹🇭",
    "Tunisian" to "🇹🇳",
    "Turkish" to "🇹🇷",
    "Ukrainian" to "🇺🇦",
    "Uruguayan" to "🇺🇾",
    "Venezuela" to "🇻🇪",
    "Vietnamese" to "🇻🇳"
)

fun CuisineOption.flagEmoji(): String = CUISINE_FLAG[cuisineLabel] ?: "🌍"
