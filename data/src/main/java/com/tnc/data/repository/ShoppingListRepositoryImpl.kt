package com.tnc.data.repository

import com.tnc.data.local.shoppinglist.ShoppingListDao
import com.tnc.data.local.shoppinglist.toDomain
import com.tnc.data.local.shoppinglist.toShoppingListEntities
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.shoppinglist.model.ShoppingListItem
import com.tnc.domain.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShoppingListRepositoryImpl(
    private val shoppingListDao: ShoppingListDao
) : ShoppingListRepository {

    override fun getItems(): Flow<List<ShoppingListItem>> =
        shoppingListDao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun addRecipe(
        recipe: Recipe
    ) {
        shoppingListDao.insertAll(recipe.toShoppingListEntities())
    }

    override suspend fun toggleChecked(
        id: String
    ) {
        val isCurrentlyChecked = shoppingListDao.isChecked(id) ?: false
        shoppingListDao.setChecked(id, !isCurrentlyChecked)
    }

    override suspend fun clearAll() {
        shoppingListDao.clearAll()
    }

}
