package com.tnc.yemivo.app.feature.common

import androidx.annotation.DrawableRes
import com.tnc.yemivo.R

/**
 * The bundled recipe data has no photo per recipe (see recipes_turkish/italian/mexican/
 * japanese.json), so every recipe falls back to one representative photo for its category —
 * far better than a generic "no image" icon, without needing thousands of individually-licensed
 * photos. Images are bundled under drawable-nodpi (Pixabay License — free for commercial use,
 * no attribution required; downloaded once rather than hotlinked per Pixabay's own guidelines).
 */
@DrawableRes
fun categoryImageRes(category: String): Int = when (category.lowercase()) {
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
