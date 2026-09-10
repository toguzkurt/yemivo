package com.tnc.data.local.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_items")
data class ShoppingListItemEntity(

    @PrimaryKey
    val id: String,

    val recipeId: String,

    val recipeName: String,

    val recipeNameTr: String? = null,

    val ingredientName: String,

    val ingredientNameTr: String? = null,

    val amount: String,

    val isChecked: Boolean

)
