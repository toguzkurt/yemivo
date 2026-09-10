package com.tnc.yemivo.app.feature.recipedetail

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.app.feature.common.displayText
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.ItemRecipeIngredientBinding
import com.tnc.core.base.BaseAdapter
import com.tnc.domain.recipe.model.RecipeIngredient

class RecipeIngredientAdapter : BaseAdapter<RecipeIngredient, ItemRecipeIngredientBinding>(
    bindingInflater = ItemRecipeIngredientBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<RecipeIngredient>() {

        override fun areItemsTheSame(
            oldItem: RecipeIngredient,
            newItem: RecipeIngredient
        ) = oldItem.name == newItem.name

        override fun areContentsTheSame(
            oldItem: RecipeIngredient,
            newItem: RecipeIngredient
        ) = oldItem == newItem

    }
) {

    override fun onBind(
        binding: ItemRecipeIngredientBinding,
        item: RecipeIngredient,
        position: Int
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(root.context) == LocaleHelper.TAG_TURKISH

        tvIngredient.text = item.displayText(isTurkish)

    }

}
