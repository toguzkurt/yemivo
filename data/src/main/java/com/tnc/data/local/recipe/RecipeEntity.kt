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

    val imageUrl: String?
)

data class RecipeIngredientEntity(
    val name: String,
    val amount: String,
    val unit: String
)
