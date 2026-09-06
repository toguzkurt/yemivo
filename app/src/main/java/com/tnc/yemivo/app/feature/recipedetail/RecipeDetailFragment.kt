package com.tnc.yemivo.app.feature.recipedetail

import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.detailSummary
import com.tnc.yemivo.databinding.FragmentRecipeDetailBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.extensions.showToast
import com.tnc.domain.recipe.model.Recipe
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RecipeDetailFragment : BaseFragment<FragmentRecipeDetailBinding>(
    FragmentRecipeDetailBinding::inflate
) {

    private val recipeId: String
        get() = requireArguments().getString(ARG_RECIPE_ID).orEmpty()

    private val viewModel: RecipeDetailViewModel by viewModel {
        parametersOf(recipeId)
    }

    private val stepAdapter = RecipeStepAdapter()

    override fun setupViews() = with(binding) {

        rvSteps.adapter = stepAdapter

    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        ivFavorite.setOnClickListener {
            viewModel.onEvent(RecipeDetailUiEvent.FavoriteClicked)
        }

        btnAddToShoppingList.setOnClickListener {
            viewModel.onEvent(RecipeDetailUiEvent.AddToShoppingListClicked)
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                state.recipe?.let(::render)

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is RecipeDetailUiEffect.ShowMessage -> {
                        requireContext().showToast(effect.message)
                    }
                }
            }

        }

    }

    private fun render(
        recipe: Recipe
    ) = with(binding) {

        tvTitle.text = recipe.name
        tvMeta.text = recipe.detailSummary()

        ivFavorite.setImageResource(
            if (recipe.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )

        stepAdapter.submitList(recipe.steps)

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
