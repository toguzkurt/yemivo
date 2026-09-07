package com.tnc.yemivo.app.feature.search

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
import com.tnc.yemivo.app.feature.common.metaSummary
import com.tnc.yemivo.databinding.ItemSearchResultBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.recipe.model.Recipe

class SearchResultAdapter(
    onItemClick: (Recipe) -> Unit
) : BaseAdapter<Recipe, ItemSearchResultBinding>(
    bindingInflater = ItemSearchResultBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(
            oldItem: Recipe,
            newItem: Recipe
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Recipe,
            newItem: Recipe
        ) = oldItem == newItem

    },
    onItemClick = onItemClick
) {

    override fun onBind(
        binding: ItemSearchResultBinding,
        item: Recipe,
        position: Int
    ) = with(binding) {

        tvName.text = item.name
        tvMeta.text = item.metaSummary()
        ivThumb.bindRecipeImage(
            recipe = item,
            iconPadding = root.resources.getDimensionPixelSize(R.dimen.spacing_sm)
        )

    }

}
