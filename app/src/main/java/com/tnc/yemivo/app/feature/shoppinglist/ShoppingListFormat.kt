package com.tnc.yemivo.app.feature.shoppinglist

import android.content.Context
import com.tnc.domain.shoppinglist.model.ShoppingListItem
import com.tnc.yemivo.app.theme.LocaleHelper

/**
 * Same "Turkish if ready and current language is Turkish, else English" fallback as
 * Recipe.displayName() (see RecipeDisplay.kt) — shopping list items snapshot both language
 * variants at add-time, this just picks which to show.
 */
fun ShoppingListItem.displayIngredientName(
    context: Context
): String {
    val isTurkish = LocaleHelper.currentTag(context) == LocaleHelper.TAG_TURKISH
    return if (isTurkish) ingredientNameTr?.takeIf { it.isNotBlank() } ?: ingredientName else ingredientName
}

fun ShoppingListItem.displayRecipeName(
    context: Context
): String {
    val isTurkish = LocaleHelper.currentTag(context) == LocaleHelper.TAG_TURKISH
    return if (isTurkish) recipeNameTr?.takeIf { it.isNotBlank() } ?: recipeName else recipeName
}

fun List<ShoppingListItem>.toShareText(
    context: Context
): String = joinToString(separator = "\n") { item ->
    "${if (item.isChecked) "✓" else "•"} ${item.amount} ${item.displayIngredientName(context)}".trim()
}
