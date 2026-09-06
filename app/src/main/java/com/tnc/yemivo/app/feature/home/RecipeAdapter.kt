package com.tnc.yemivo.app.feature.home

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.categoryImageRes
import com.tnc.yemivo.app.feature.common.metaSummary
import com.tnc.yemivo.databinding.ItemRecipeCardBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.recipe.model.Recipe

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

        tvName.text = item.name
        tvMeta.text = item.metaSummary()
        ivThumb.setImageResource(categoryImageRes(item.category))

        ivFavorite.setImageResource(
            if (item.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )

        ivFavorite.setOnClickListener {
            onFavoriteClick(item)
        }

    }

}
