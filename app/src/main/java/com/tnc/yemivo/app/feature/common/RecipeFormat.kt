package com.tnc.yemivo.app.feature.common

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.RecipeIngredient
import java.util.Locale

/**
 * Short "cuisine · info" line used on recipe cards and the detail screen. The bundled recipe
 * data (see RecipeSeeder) rarely has both prep and cook time filled in — falls back to the
 * recipe's category (e.g. "Ana Yemek") when neither is available, rather than showing "0 dk".
 */
fun Recipe.metaSummary(): String {

    val totalMinutes = (prepMinutes ?: 0) + (cookMinutes ?: 0)

    val secondary = if (totalMinutes > 0) {
        "$totalMinutes dk"
    } else {
        category.titleCaseTr()
    }

    return "$cuisineLabel · $secondary"
}

/**
 * "Türk mutfağı · 45 dk · 4 kişilik" style full summary for the recipe detail header.
 */
fun Recipe.detailSummary(): String {

    val totalMinutes = (prepMinutes ?: 0) + (cookMinutes ?: 0)

    return buildList {
        add(cuisineLabel)
        if (totalMinutes > 0) add("$totalMinutes dk") else add(category.titleCaseTr())
        servings?.let { add("$it kişilik") }
    }.joinToString(" · ")

}

/**
 * "2 cups Flour" style line for the ingredients tab and the shopping list — amount and unit are
 * two separate free-text fields (see RecipeIngredient), either of which can be blank depending
 * on the data source, so join only the non-blank ones.
 */
fun RecipeIngredient.displayText(): String =
    listOf(amount, unit, name)
        .filter { it.isNotBlank() }
        .joinToString(" ")

private fun String.titleCaseTr(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase(Locale.forLanguageTag("tr")) }
    }
