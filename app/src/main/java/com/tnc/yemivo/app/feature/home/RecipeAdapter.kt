package com.tnc.yemivo.app.feature.home

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
import com.tnc.yemivo.app.feature.common.metaSummary
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.ItemRecipeCardBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displayName

class RecipeAdapter(
    onItemClick: (Recipe) -> Unit,
    private val onFavoriteClick: (Recipe) -> Unit
) : BaseAdapter<Recipe, ItemRecipeCardBinding>(
    bindingInflater = ItemRecipeCardBinding::inflate,
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
        binding: ItemRecipeCardBinding,
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

        ivFavorite.setImageResource(
            if (item.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )

        ivFavorite.setOnClickListener {
            onFavoriteClick(item)
        }

    }

}
