package com.tnc.domain.shoppinglist.model

data class ShoppingListItem(

    val id: String,

    val recipeId: String,

    val recipeName: String,

    val recipeNameTr: String? = null,

    val ingredientName: String,

    val ingredientNameTr: String? = null,

    val amount: String,

    val isChecked: Boolean

)
