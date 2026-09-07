package com.tnc.yemivo.app.feature.shoppinglist

import com.tnc.core.base.BaseViewModel
import com.tnc.domain.shoppinglist.model.ShoppingListItem
import com.tnc.domain.shoppinglist.usecase.ClearShoppingListUseCase
import com.tnc.domain.shoppinglist.usecase.GetShoppingListUseCase
import com.tnc.domain.shoppinglist.usecase.ToggleShoppingListItemUseCase

class ShoppingListViewModel(
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val toggleShoppingListItemUseCase: ToggleShoppingListItemUseCase,
    private val clearShoppingListUseCase: ClearShoppingListUseCase
) : BaseViewModel<ShoppingListUiState, ShoppingListUiEffect>(initialState = ShoppingListUiState()) {

    init {
        observeItems()
    }

    fun onEvent(
        event: ShoppingListUiEvent
    ) {
        when (event) {

            is ShoppingListUiEvent.ItemToggled -> {
                launch { toggleShoppingListItemUseCase(event.id) }
            }

            ShoppingListUiEvent.ClearAllClicked -> {
                launch { clearShoppingListUseCase() }
            }

            ShoppingListUiEvent.ShareClicked -> {
                sendEffect(ShoppingListUiEffect.ShareList(buildShareText(state.value.items)))
            }

        }
    }

    private fun observeItems() {

        launch {

            getShoppingListUseCase().collect { items ->

                setState { copy(items = items) }

            }

        }

    }

    private fun buildShareText(
        items: List<ShoppingListItem>
    ): String = items.joinToString(separator = "\n") { item ->
        "${if (item.isChecked) "✓" else "•"} ${item.amount} ${item.ingredientName}".trim()
    }

}
