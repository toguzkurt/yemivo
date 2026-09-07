package com.tnc.yemivo.app.feature.shoppinglist

import com.tnc.domain.shoppinglist.model.ShoppingListItem

data class ShoppingListUiState(

    val items: List<ShoppingListItem> = emptyList()

)

sealed interface ShoppingListUiEvent {

    data class ItemToggled(
        val id: String
    ) : ShoppingListUiEvent

    data object ClearAllClicked : ShoppingListUiEvent

    data object ShareClicked : ShoppingListUiEvent

}

sealed interface ShoppingListUiEffect {

    data class ShareList(
        val text: String
    ) : ShoppingListUiEffect

}
