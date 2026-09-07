package com.tnc.yemivo.app.feature.common

import androidx.annotation.DrawableRes
import com.tnc.yemivo.R

/**
 * Exact-name overrides for the handful of recipes we found a genuinely accurate photo for
 * (verified by eye, not just keyword-matched — e.g. a "ramen" search that actually returned
 * packaged instant noodles was rejected). Everything else falls back to [categoryImageRes].
 * Photos are Pixabay License (free for commercial use, no attribution required), downloaded
 * once and bundled under drawable-nodpi rather than hotlinked.
 */
@DrawableRes
fun dishImageRes(recipeName: String): Int? = when (recipeName) {
    "Menemen" -> R.drawable.dish_menemen
    "Ev Yapımı Antep Fıstıklı Baklava" -> R.drawable.dish_baklava
    "Margherita Pizza" -> R.drawable.dish_margherita_pizza
    "Spaghetti Carbonara" -> R.drawable.dish_spaghetti_carbonara
    "Lazanya" -> R.drawable.dish_lasagna
    "Tiramisu" -> R.drawable.dish_tiramisu
    "Tavuklu Taco" -> R.drawable.dish_taco
    "Guacamole" -> R.drawable.dish_guacamole
    "Churros" -> R.drawable.dish_churros
    "California Roll" -> R.drawable.dish_sushi
    else -> null
}
