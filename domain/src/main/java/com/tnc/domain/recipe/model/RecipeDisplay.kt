package com.tnc.domain.recipe.model

/**
 * Picks the Turkish field when it's ready and the current UI language is Turkish, falling back
 * to the English source field otherwise (translation not done yet, or app language is English).
 * Kept dependency-free so both the `data` layer (notification/shopping-list snapshots) and the
 * `app` layer (UI) can share the same fallback logic.
 */
fun Recipe.displayName(isTurkish: Boolean): String =
    if (isTurkish) nameTr?.takeIf { it.isNotBlank() } ?: name else name

fun Recipe.displaySteps(isTurkish: Boolean): List<String> =
    if (isTurkish) stepsTr?.takeIf { it.isNotEmpty() } ?: steps else steps

fun RecipeIngredient.displayName(isTurkish: Boolean): String =
    if (isTurkish) nameTr?.takeIf { it.isNotBlank() } ?: name else name
