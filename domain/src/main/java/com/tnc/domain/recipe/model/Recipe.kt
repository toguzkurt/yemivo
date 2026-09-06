package com.tnc.domain.recipe.model

data class Recipe(

    val id: String,

    val name: String,

    val cuisine: String,

    val cuisineLabel: String,

    val durationMinutes: Int,

    val servings: Int,

    val isFavorite: Boolean,

    val steps: List<String>
)
