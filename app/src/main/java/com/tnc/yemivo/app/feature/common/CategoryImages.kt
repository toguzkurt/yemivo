package com.tnc.yemivo.app.feature.common

import androidx.annotation.DrawableRes
import com.tnc.yemivo.R

/**
 * Rarely shown now that recipes carry a real photo URL from TheMealDB (see
 * RecipeImageBinding.kt) — only used as a loading placeholder / last-resort fallback when a
 * photo is missing or fails to load. TheMealDB's own categories are English (Dessert, Starter,
 * Breakfast, ...), unlike the old bundled-JSON Turkish taxonomy this once matched against.
 * Images are bundled under drawable-nodpi (Pixabay License — free for commercial use, no
 * attribution required; downloaded once rather than hotlinked per Pixabay's own guidelines).
 */
@DrawableRes
fun categoryImageRes(category: String): Int = when (category.lowercase()) {
    "dessert" -> R.drawable.cat_tatli
    "starter" -> R.drawable.cat_atistirmalik
    "breakfast" -> R.drawable.cat_kahvalti
    "side" -> R.drawable.cat_salata
    // Legacy Turkish taxonomy, kept in case the bundled-JSON seeder is ever reactivated.
    "ana yemek" -> R.drawable.cat_ana_yemek
    "çorba" -> R.drawable.cat_corba
    "tatlı" -> R.drawable.cat_tatli
    "salata" -> R.drawable.cat_salata
    "kahvaltı" -> R.drawable.cat_kahvalti
    "atıştırmalık" -> R.drawable.cat_atistirmalik
    "içecek" -> R.drawable.cat_icecek
    "turşu" -> R.drawable.cat_tursu
    else -> R.drawable.cat_ana_yemek
}
