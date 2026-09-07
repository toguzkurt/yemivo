package com.tnc.yemivo.app.feature.recipedetail

import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
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

    private val ingredientAdapter = RecipeIngredientAdapter()
    private val stepAdapter = RecipeStepAdapter()

    override fun setupViews() = with(binding) {

        rvIngredients.adapter = ingredientAdapter
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

        ivDownload.setOnClickListener {
            viewModel.onEvent(RecipeDetailUiEvent.DownloadClicked)
        }

        tabIngredients.setOnClickListener {
            viewModel.onEvent(RecipeDetailUiEvent.TabSelected(RecipeDetailTab.INGREDIENTS))
        }

        tabInstructions.setOnClickListener {
            viewModel.onEvent(RecipeDetailUiEvent.TabSelected(RecipeDetailTab.INSTRUCTIONS))
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                state.recipe?.let { render(it, state.selectedTab) }

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
        recipe: Recipe,
        selectedTab: RecipeDetailTab
    ) = with(binding) {

        tvTitle.text = recipe.name
        tvMeta.text = recipe.detailSummary()
        ivHero.bindRecipeImage(
            recipe = recipe,
            iconPadding = resources.getDimensionPixelSize(R.dimen.spacing_huge)
        )

        ivFavorite.setImageResource(
            if (recipe.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )

        ivDownload.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                if (recipe.isDownloaded) R.color.accent else R.color.text_secondary
            )
        )

        ingredientAdapter.submitList(recipe.ingredients)
        stepAdapter.submitList(recipe.steps)

        val showIngredients = selectedTab == RecipeDetailTab.INGREDIENTS
        rvIngredients.isVisible = showIngredients
        rvSteps.isVisible = !showIngredients

        renderTab(tabIngredients, showIngredients)
        renderTab(tabInstructions, !showIngredients)

    }

    private fun renderTab(
        tab: TextView,
        isActive: Boolean
    ) {
        tab.setBackgroundResource(
            if (isActive) R.drawable.bg_tab_active else R.drawable.bg_tab_inactive
        )
        tab.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isActive) R.color.accent else R.color.text_secondary
            )
        )
        tab.setTypeface(tab.typeface, if (isActive) Typeface.BOLD else Typeface.NORMAL)
    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
