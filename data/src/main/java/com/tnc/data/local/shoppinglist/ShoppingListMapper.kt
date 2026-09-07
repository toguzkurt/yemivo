package com.tnc.data.local.shoppinglist

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.shoppinglist.model.ShoppingListItem

fun ShoppingListItemEntity.toDomain(): ShoppingListItem = ShoppingListItem(
    id = id,
    recipeId = recipeId,
    recipeName = recipeName,
    ingredientName = ingredientName,
    amount = amount,
    isChecked = isChecked
)

fun Recipe.toShoppingListEntities(): List<ShoppingListItemEntity> = ingredients.map { ingredient ->
    ShoppingListItemEntity(
        id = "$id::${ingredient.name}",
        recipeId = id,
        recipeName = name,
        ingredientName = ingredient.name,
        amount = listOf(ingredient.amount, ingredient.unit)
            .filter { it.isNotBlank() }
            .joinToString(" "),
        isChecked = false
    )
}
