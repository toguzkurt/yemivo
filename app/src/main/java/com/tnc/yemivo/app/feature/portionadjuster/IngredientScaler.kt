package com.tnc.yemivo.app.feature.portionadjuster

import com.tnc.domain.recipe.model.RecipeIngredient
import com.tnc.domain.recipe.model.displayName
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * TheMealDB (this app's only recipe source) never provides a servings count — MealMapper.kt
 * hardcodes Recipe.servings to null for every recipe — so portion scaling needs an assumed
 * baseline to scale from. 4 matches a standard single-recipe serving size.
 */
const val DEFAULT_SERVINGS = 4

data class ScaledIngredient(
    val displayName: String,
    val displayAmount: String,
    val isHighlighted: Boolean
)

/**
 * Recalculates this ingredient's amount for a different serving count. Amounts too free-form to
 * parse (e.g. "a pinch", "to taste") are left untouched rather than guessed at.
 */
fun RecipeIngredient.scaledFor(
    originalServings: Int,
    targetServings: Int,
    isTurkish: Boolean
): ScaledIngredient {

    val name = displayName(isTurkish)
    val parsed = if (targetServings != originalServings) parseLeadingNumber(amount) else null

    if (parsed == null) {
        return ScaledIngredient(
            displayName = name,
            displayAmount = listOf(amount, unit).filter { it.isNotBlank() }.joinToString(" "),
            isHighlighted = false
        )
    }

    val (value, suffix) = parsed
    val scaledValue = value * targetServings / originalServings
    val scaledAmount = formatScaledAmount(scaledValue) + suffix

    return ScaledIngredient(
        displayName = name,
        displayAmount = listOf(scaledAmount, unit).filter { it.isNotBlank() }.joinToString(" "),
        isHighlighted = true
    )

}

private fun parseLeadingNumber(text: String): Pair<Double, String>? {

    val trimmed = text.trim()

    Regex("""^(\d+)\s+(\d+)/(\d+)""").find(trimmed)?.let { m ->
        val whole = m.groupValues[1].toDouble()
        val num = m.groupValues[2].toDouble()
        val den = m.groupValues[3].toDouble()
        return (whole + num / den) to trimmed.substring(m.value.length)
    }

    Regex("""^(\d+)/(\d+)""").find(trimmed)?.let { m ->
        val num = m.groupValues[1].toDouble()
        val den = m.groupValues[2].toDouble()
        return (num / den) to trimmed.substring(m.value.length)
    }

    Regex("""^(\d+(?:[.,]\d+)?)""").find(trimmed)?.let { m ->
        return m.groupValues[1].replace(',', '.').toDouble() to trimmed.substring(m.value.length)
    }

    return null

}

private fun formatScaledAmount(value: Double): String {

    val rounded = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP)

    if (rounded.compareTo(BigDecimal.ZERO) == 0) return "0"

    return rounded.stripTrailingZeros().toPlainString()

}
