package com.tnc.yemivo.app.feature.search

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
import com.tnc.yemivo.app.feature.common.metaSummary
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.ItemSearchResultBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displayName

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

        val isTurkish = LocaleHelper.currentTag(root.context) == LocaleHelper.TAG_TURKISH

        tvName.text = item.displayName(isTurkish)
        tvMeta.text = item.metaSummary(isTurkish)
        ivThumb.bindRecipeImage(
            recipe = item,
            iconPadding = root.resources.getDimensionPixelSize(R.dimen.spacing_sm)
        )

    }

}
