package com.tnc.domain.shoppinglist.usecase

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.shoppinglist.repository.ShoppingListRepository

class AddRecipeToShoppingListUseCase(
    private val repository: ShoppingListRepository
) {

    suspend operator fun invoke(
        recipe: Recipe
    ) {
        repository.addRecipe(recipe)
    }

}
