package com.tnc.yemivo.app.feature.common

import android.widget.ImageView
import coil.load
import com.tnc.domain.recipe.model.Recipe

/**
 * TheMealDB ships a real photo (imageUrl) for essentially every recipe, so that's the primary
 * path now. [dishImageRes] (a handful of hand-verified bundled photos from the old bundled-JSON
 * data source) and [categoryImageRes] stay as fallbacks for the rare missing/failed load.
 */
fun ImageView.bindRecipeImage(
    recipe: Recipe,
    iconPadding: Int
) {
    val fallbackRes = dishImageRes(recipe.name) ?: categoryImageRes(recipe.category)

    if (!recipe.imageUrl.isNullOrBlank()) {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setPadding(0, 0, 0, 0)
        clipToOutline = true
        load(recipe.imageUrl) {
            crossfade(true)
            placeholder(fallbackRes)
            error(fallbackRes)
        }
    } else if (dishImageRes(recipe.name) != null) {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setPadding(0, 0, 0, 0)
        clipToOutline = true
        setImageResource(fallbackRes)
    } else {
        scaleType = ImageView.ScaleType.CENTER_INSIDE
        setPadding(iconPadding, iconPadding, iconPadding, iconPadding)
        clipToOutline = false
        setImageResource(fallbackRes)
    }
}
