package com.tnc.yemivo.app.feature.cuisinerecipes

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.cuisineLabelTr
import com.tnc.yemivo.app.feature.home.RecipeAdapter
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.FragmentCuisineRecipesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CuisineRecipesFragment : BaseFragment<FragmentCuisineRecipesBinding>(
    FragmentCuisineRecipesBinding::inflate
) {

    private val cuisine: String
        get() = requireArguments().getString(ARG_CUISINE).orEmpty()

    private val cuisineLabel: String
        get() = requireArguments().getString(ARG_CUISINE_LABEL).orEmpty()

    private val viewModel: CuisineRecipesViewModel by viewModel {
        parametersOf(cuisine, cuisineLabel)
    }

    private val adapter = RecipeAdapter(
        onItemClick = { recipe ->
            viewModel.onEvent(CuisineRecipesUiEvent.RecipeClicked(recipe.id))
        },
        onFavoriteClick = { recipe ->
            viewModel.onEvent(CuisineRecipesUiEvent.FavoriteClicked(recipe.id))
        }
    )

    override fun setupViews() = with(binding) {
        rvRecipes.adapter = adapter
    }

    override fun setupListeners() = with(binding) {
        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                val isTurkish = LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH

                binding.tvTitle.text = getString(
                    R.string.cuisines_cell_name,
                    state.cuisineLabel.cuisineLabelTr(isTurkish)
                )
                adapter.submitList(state.recipes)

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is CuisineRecipesUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_cuisineRecipes_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }
                }
            }

        }

    }

    private companion object {
        const val ARG_CUISINE = "cuisine"
        const val ARG_CUISINE_LABEL = "cuisineLabel"
        const val ARG_RECIPE_ID = "recipeId"
    }

}
