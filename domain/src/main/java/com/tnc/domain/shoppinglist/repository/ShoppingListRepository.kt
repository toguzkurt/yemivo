package com.tnc.domain.shoppinglist.repository

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.shoppinglist.model.ShoppingListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    fun getItems(): Flow<List<ShoppingListItem>>

    suspend fun addRecipe(
        recipe: Recipe
    )

    suspend fun toggleChecked(
        id: String
    )

    suspend fun clearAll()

}
