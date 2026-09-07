package com.tnc.domain.shoppinglist.usecase

import com.tnc.domain.shoppinglist.repository.ShoppingListRepository

class ToggleShoppingListItemUseCase(
    private val repository: ShoppingListRepository
) {

    suspend operator fun invoke(
        id: String
    ) {
        repository.toggleChecked(id)
    }

}
