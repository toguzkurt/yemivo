package com.tnc.yemivo.app.feature.common

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import com.tnc.core.base.BaseAdapter
import com.tnc.yemivo.R
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.ItemCuisineChipBinding

/**
 * cuisine == null represents the "All" chip (fixed @string/cuisine_all label, not translated
 * via the cuisine map). Shared between Home and Search — same filter chip row in both.
 */
data class CuisineChipItem(
    val cuisine: String?,
    val cuisineLabel: String?,
    val isSelected: Boolean
)

class CuisineChipAdapter(
    onChipClick: (String?) -> Unit
) : BaseAdapter<CuisineChipItem, ItemCuisineChipBinding>(
    bindingInflater = ItemCuisineChipBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<CuisineChipItem>() {

        override fun areItemsTheSame(
            oldItem: CuisineChipItem,
            newItem: CuisineChipItem
        ) = oldItem.cuisine == newItem.cuisine

        override fun areContentsTheSame(
            oldItem: CuisineChipItem,
            newItem: CuisineChipItem
        ) = oldItem == newItem

    },
    onItemClick = { onChipClick(it.cuisine) }
) {

    override fun onBind(
        binding: ItemCuisineChipBinding,
        item: CuisineChipItem,
        position: Int
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(root.context) == LocaleHelper.TAG_TURKISH

        root.text = item.cuisine?.let { item.cuisineLabel!!.cuisineLabelTr(isTurkish) }
            ?: root.context.getString(R.string.cuisine_all)

        root.setBackgroundResource(
            if (item.isSelected) R.drawable.bg_chip_active else R.drawable.bg_chip_outline
        )

        root.setTextColor(
            ContextCompat.getColor(
                root.context,
                if (item.isSelected) R.color.accent else R.color.text_secondary
            )
        )

    }

}
