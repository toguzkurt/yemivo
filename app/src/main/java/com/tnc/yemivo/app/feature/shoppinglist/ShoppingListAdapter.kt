package com.tnc.yemivo.app.feature.shoppinglist

import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.ItemShoppingListBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.shoppinglist.model.ShoppingListItem

class ShoppingListAdapter(
    onItemClick: (ShoppingListItem) -> Unit
) : BaseAdapter<ShoppingListItem, ItemShoppingListBinding>(
    bindingInflater = ItemShoppingListBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<ShoppingListItem>() {

        override fun areItemsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ShoppingListItem,
            newItem: ShoppingListItem
        ) = oldItem == newItem

    },
    onItemClick = onItemClick
) {

    override fun onBind(
        binding: ItemShoppingListBinding,
        item: ShoppingListItem,
        position: Int
    ) = with(binding) {

        tvName.text = "${item.amount} ${item.ingredientName}".trim()
        tvTag.text = item.recipeName

        checkbox.setBackgroundResource(
            if (item.isChecked) R.drawable.bg_checkbox_checked else R.drawable.bg_checkbox_unchecked
        )

        tvName.paintFlags = if (item.isChecked) {
            tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        tvName.setTextColor(
            ContextCompat.getColor(
                root.context,
                if (item.isChecked) R.color.text_muted else R.color.text_primary
            )
        )

    }

}
