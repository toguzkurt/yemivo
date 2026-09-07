package com.tnc.domain.shoppinglist.model

data class ShoppingListItem(

    val id: String,

    val recipeId: String,

    val recipeName: String,

    val ingredientName: String,

    val amount: String,

    val isChecked: Boolean

)
