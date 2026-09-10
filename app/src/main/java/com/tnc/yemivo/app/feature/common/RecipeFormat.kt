package com.tnc.yemivo.app.feature.common

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.RecipeIngredient
import com.tnc.domain.recipe.model.displayName
import java.util.Locale

/**
 * TheMealDB's area/category vocabulary is small and fixed, so these are translated with a
 * hand-curated map instead of going through ML Kit — faster, free, and more accurate than a
 * generic machine translation for single-word cuisine/category names.
 */
private val CUISINE_LABEL_TR = mapOf(
    "Algerian" to "Cezayir",
    "American" to "Amerikan",
    "Argentina" to "Arjantin",
    "Australian" to "Avustralya",
    "British" to "İngiliz",
    "Canadian" to "Kanada",
    "Chinese" to "Çin",
    "Croatian" to "Hırvat",
    "Dutch" to "Hollanda",
    "Egyptian" to "Mısır",
    "Filipino" to "Filipin",
    "France" to "Fransa",
    "French" to "Fransız",
    "Greek" to "Yunan",
    "Indian" to "Hint",
    "Irish" to "İrlanda",
    "Italian" to "İtalyan",
    "Jamaican" to "Jamaika",
    "Japanese" to "Japon",
    "Kenyan" to "Kenya",
    "Malaysian" to "Malezya",
    "Mexican" to "Meksika",
    "Moroccan" to "Fas",
    "Netherlands" to "Hollanda",
    "Norway" to "Norveç",
    "Polish" to "Polonya",
    "Portuguese" to "Portekiz",
    "Russian" to "Rus",
    "Saudi Arabian" to "Suudi Arabistan",
    "Slovakia" to "Slovakya",
    "Spanish" to "İspanyol",
    "Syrian" to "Suriye",
    "Thai" to "Tayland",
    "Tunisian" to "Tunus",
    "Turkish" to "Türk",
    "Ukrainian" to "Ukrayna",
    "Venezuela" to "Venezuela",
    "Uruguayan" to "Uruguay",
    "Vietnamese" to "Vietnam"
)

private val CATEGORY_LABEL_TR = mapOf(
    "Beef" to "Dana Eti",
    "Breakfast" to "Kahvaltı",
    "Chicken" to "Tavuk",
    "Dessert" to "Tatlı",
    "Goat" to "Keçi Eti",
    "Lamb" to "Kuzu Eti",
    "Miscellaneous" to "Diğer",
    "Pasta" to "Makarna",
    "Pork" to "Domuz Eti",
    "Seafood" to "Deniz Ürünleri",
    "Side" to "Yan Yemek",
    "Starter" to "Başlangıç",
    "Vegan" to "Vegan",
    "Vegetarian" to "Vejetaryen"
)

internal fun String.cuisineLabelTr(isTurkish: Boolean): String =
    if (isTurkish) CUISINE_LABEL_TR[this] ?: this else this

private fun String.categoryLabelTr(isTurkish: Boolean): String =
    if (isTurkish) CATEGORY_LABEL_TR[this] ?: titleCaseTr() else titleCaseTr()

/**
 * Short "cuisine · info" line used on recipe cards and the detail screen. The bundled recipe
 * data (see RecipeSeeder) rarely has both prep and cook time filled in — falls back to the
 * recipe's category (e.g. "Ana Yemek") when neither is available, rather than showing "0 dk".
 */
fun Recipe.metaSummary(isTurkish: Boolean): String {

    val totalMinutes = (prepMinutes ?: 0) + (cookMinutes ?: 0)

    val secondary = if (totalMinutes > 0) {
        "$totalMinutes dk"
    } else {
        category.categoryLabelTr(isTurkish)
    }

    return "${cuisineLabel.cuisineLabelTr(isTurkish)} · $secondary"
}

/**
 * "Türk mutfağı · 45 dk · 4 kişilik" style full summary for the recipe detail header.
 */
fun Recipe.detailSummary(isTurkish: Boolean): String {

    val totalMinutes = (prepMinutes ?: 0) + (cookMinutes ?: 0)

    return buildList {
        add(cuisineLabel.cuisineLabelTr(isTurkish))
        if (totalMinutes > 0) add("$totalMinutes dk") else add(category.categoryLabelTr(isTurkish))
        servings?.let { add("$it kişilik") }
    }.joinToString(" · ")

}

/**
 * "2 cups Flour" style line for the ingredients tab and the shopping list — amount and unit are
 * two separate free-text fields (see RecipeIngredient), either of which can be blank depending
 * on the data source, so join only the non-blank ones.
 */
fun RecipeIngredient.displayText(isTurkish: Boolean): String =
    listOf(amount, unit, displayName(isTurkish))
        .filter { it.isNotBlank() }
        .joinToString(" ")

private fun String.titleCaseTr(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase(Locale.forLanguageTag("tr")) }
    }
