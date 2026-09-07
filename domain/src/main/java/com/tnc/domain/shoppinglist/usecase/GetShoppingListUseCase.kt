package com.tnc.domain.shoppinglist.usecase

import com.tnc.domain.shoppinglist.model.ShoppingListItem
import com.tnc.domain.shoppinglist.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow

class GetShoppingListUseCase(
    private val repository: ShoppingListRepository
) {

    operator fun invoke(): Flow<List<ShoppingListItem>> = repository.getItems()

}
