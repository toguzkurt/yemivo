package com.tnc.domain.shoppinglist.usecase

import com.tnc.domain.shoppinglist.repository.ShoppingListRepository

class ClearShoppingListUseCase(
    private val repository: ShoppingListRepository
) {

    suspend operator fun invoke() {
        repository.clearAll()
    }

}
