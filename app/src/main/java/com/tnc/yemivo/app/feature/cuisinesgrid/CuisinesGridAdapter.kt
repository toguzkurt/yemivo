package com.tnc.yemivo.app.feature.cuisinesgrid

import androidx.recyclerview.widget.DiffUtil
import com.tnc.core.base.BaseAdapter
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.CuisineOption
import com.tnc.yemivo.app.feature.common.cuisineLabelTr
import com.tnc.yemivo.app.feature.common.flagEmoji
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.ItemCuisineCellBinding

class CuisinesGridAdapter(
    onItemClick: (CuisineOption) -> Unit
) : BaseAdapter<CuisineOption, ItemCuisineCellBinding>(
    bindingInflater = ItemCuisineCellBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<CuisineOption>() {

        override fun areItemsTheSame(
            oldItem: CuisineOption,
            newItem: CuisineOption
        ) = oldItem.cuisine == newItem.cuisine

        override fun areContentsTheSame(
            oldItem: CuisineOption,
            newItem: CuisineOption
        ) = oldItem == newItem

    },
    onItemClick = onItemClick
) {

    override fun onBind(
        binding: ItemCuisineCellBinding,
        item: CuisineOption,
        position: Int
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(root.context) == LocaleHelper.TAG_TURKISH

        tvFlag.text = item.flagEmoji()
        tvName.text = root.context.getString(
            R.string.cuisines_cell_name,
            item.cuisineLabel.cuisineLabelTr(isTurkish)
        )
        tvCount.text = root.context.getString(R.string.cuisines_recipe_count, item.count)

        root.setBackgroundResource(CELL_BACKGROUNDS[position % CELL_BACKGROUNDS.size])

    }

    private companion object {
        val CELL_BACKGROUNDS = intArrayOf(
            R.drawable.bg_cuisine_cell_1,
            R.drawable.bg_cuisine_cell_2,
            R.drawable.bg_cuisine_cell_3,
            R.drawable.bg_cuisine_cell_4,
            R.drawable.bg_cuisine_cell_5,
            R.drawable.bg_cuisine_cell_6
        )
    }

}
