package com.tnc.data.local.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val cuisine: String,

    val cuisineLabel: String,

    val category: String,

    val prepMinutes: Int?,

    val cookMinutes: Int?,

    val servings: Int?,

    val difficulty: String?,

    val ingredients: List<RecipeIngredientEntity>,

    val steps: List<String>,

    val isFavorite: Boolean,

    val isDownloaded: Boolean,

    val imageUrl: String?,

    // Null until RecipeTranslator's one-time background pass fills them in — UI falls back to
    // the English fields above until then.
    val nameTr: String? = null,

    val stepsTr: List<String>? = null
)

data class RecipeIngredientEntity(
    val name: String,
    val amount: String,
    val unit: String,
    val nameTr: String? = null
)
