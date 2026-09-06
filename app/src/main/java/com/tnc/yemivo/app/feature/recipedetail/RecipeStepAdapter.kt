package com.tnc.yemivo.app.feature.recipedetail

import androidx.recyclerview.widget.DiffUtil
import com.tnc.yemivo.databinding.ItemRecipeStepBinding
import com.tnc.core.base.BaseAdapter

class RecipeStepAdapter : BaseAdapter<String, ItemRecipeStepBinding>(
    bindingInflater = ItemRecipeStepBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ) = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ) = oldItem == newItem

    }
) {

    override fun onBind(
        binding: ItemRecipeStepBinding,
        item: String,
        position: Int
    ) = with(binding) {

        tvStepNumber.text = (position + 1).toString()
        tvStepText.text = item

    }

}
