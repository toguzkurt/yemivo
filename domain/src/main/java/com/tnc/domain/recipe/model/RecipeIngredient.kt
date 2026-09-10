package com.tnc.domain.recipe.model

data class RecipeIngredient(

    val name: String,

    val amount: String,

    val unit: String,

    val nameTr: String? = null
)
