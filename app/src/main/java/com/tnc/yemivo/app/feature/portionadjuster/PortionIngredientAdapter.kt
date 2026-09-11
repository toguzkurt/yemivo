package com.tnc.yemivo.app.feature.portionadjuster

import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.tnc.core.base.BaseAdapter
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.ItemIngredientBinding

class PortionIngredientAdapter : BaseAdapter<ScaledIngredient, ItemIngredientBinding>(
    bindingInflater = ItemIngredientBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<ScaledIngredient>() {

        override fun areItemsTheSame(
            oldItem: ScaledIngredient,
            newItem: ScaledIngredient
        ) = oldItem.displayName == newItem.displayName

        override fun areContentsTheSame(
            oldItem: ScaledIngredient,
            newItem: ScaledIngredient
        ) = oldItem == newItem

    }
) {

    override fun onBind(
        binding: ItemIngredientBinding,
        item: ScaledIngredient,
        position: Int
    ) = with(binding) {

        tvName.text = item.displayName
        tvAmount.text = item.displayAmount

        tvAmount.setTextColor(
            ContextCompat.getColor(
                root.context,
                if (item.isHighlighted) R.color.accent else R.color.text_secondary
            )
        )
        tvAmount.setTypeface(tvAmount.typeface, if (item.isHighlighted) Typeface.BOLD else Typeface.NORMAL)

    }

}
