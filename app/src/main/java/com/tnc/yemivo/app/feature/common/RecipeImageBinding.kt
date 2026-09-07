package com.tnc.yemivo.app.feature.common

import android.widget.ImageView
import com.tnc.domain.recipe.model.Recipe

/**
 * Shows [dishImageRes] when we have a verified accurate photo for this exact recipe (full-bleed
 * crop), otherwise falls back to the [categoryImageRes] icon (inset with padding, so it reads as
 * a placeholder rather than a bad photo match) — see DishImages.kt for why so few recipes have
 * one.
 */
fun ImageView.bindRecipeImage(
    recipe: Recipe,
    iconPadding: Int
) {
    val dishRes = dishImageRes(recipe.name)

    if (dishRes != null) {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setPadding(0, 0, 0, 0)
        clipToOutline = true
        setImageResource(dishRes)
    } else {
        scaleType = ImageView.ScaleType.CENTER_INSIDE
        setPadding(iconPadding, iconPadding, iconPadding, iconPadding)
        clipToOutline = false
        setImageResource(categoryImageRes(recipe.category))
    }
}
