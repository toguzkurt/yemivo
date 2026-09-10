package com.tnc.data.local.shoppinglist

import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.shoppinglist.model.ShoppingListItem

fun ShoppingListItemEntity.toDomain(): ShoppingListItem = ShoppingListItem(
    id = id,
    recipeId = recipeId,
    recipeName = recipeName,
    recipeNameTr = recipeNameTr,
    ingredientName = ingredientName,
    ingredientNameTr = ingredientNameTr,
    amount = amount,
    isChecked = isChecked
)

fun Recipe.toShoppingListEntities(): List<ShoppingListItemEntity> = ingredients.map { ingredient ->
    ShoppingListItemEntity(
        // Keeps using the raw English name for id stability even after translation lands.
        id = "$id::${ingredient.name}",
        recipeId = id,
        recipeName = name,
        recipeNameTr = nameTr,
        ingredientName = ingredient.name,
        ingredientNameTr = ingredient.nameTr,
        amount = listOf(ingredient.amount, ingredient.unit)
            .filter { it.isNotBlank() }
            .joinToString(" "),
        isChecked = false
    )
}
