package com.tnc.yemivo.app.feature.favorites

import com.tnc.yemivo.databinding.FragmentFavoritesBinding
import com.tnc.core.base.BaseFragment

/**
 * Layout-only stub for now — wiring this up to GetRecipesUseCase (filtered to favorites) is
 * next in line after Home/RecipeDetail establish the pattern.
 */
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = Unit

    override fun observeState() = Unit

}
