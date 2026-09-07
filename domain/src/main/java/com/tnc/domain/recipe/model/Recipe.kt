package com.tnc.domain.recipe.model

data class Recipe(

    val id: String,

    val name: String,

    val cuisine: String,

    val cuisineLabel: String,

    val category: String,

    val prepMinutes: Int?,

    val cookMinutes: Int?,

    val servings: Int?,

    val difficulty: String?,

    val ingredients: List<RecipeIngredient>,

    val steps: List<String>,

    val isFavorite: Boolean,

    val imageUrl: String?
)
