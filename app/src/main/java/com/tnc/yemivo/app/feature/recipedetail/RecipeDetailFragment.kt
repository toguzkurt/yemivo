package com.tnc.yemivo.app.feature.recipedetail

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
import com.tnc.yemivo.app.feature.common.detailSummary
import com.tnc.yemivo.app.feature.portionadjuster.DEFAULT_SERVINGS
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.FragmentRecipeDetailBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.extensions.showToast
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displayName
import com.tnc.domain.recipe.model.displaySteps
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

        rowPortionEntry.setOnClickListener {
            findNavController().navigate(
                R.id.action_recipeDetail_to_portionAdjuster,
                Bundle().apply { putString(ARG_RECIPE_ID, recipeId) }
            )
        }

        rowCookModeEntry.setOnClickListener {
            findNavController().navigate(
                R.id.action_recipeDetail_to_cookMode,
                Bundle().apply { putString(ARG_RECIPE_ID, recipeId) }
            )
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

        val isTurkish = LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH

        tvTitle.text = recipe.displayName(isTurkish)
        tvMeta.text = recipe.detailSummary(isTurkish)
        ivHero.bindRecipeImage(
            recipe = recipe,
            iconPadding = resources.getDimensionPixelSize(R.dimen.spacing_huge)
        )

        // Both icons sit on a fixed-white bg_circle_white badge over the hero photo — the
        // filled heart's own red fillColor is left untouched, but the outline heart and the
        // "not downloaded" download icon would otherwise pick up the app's theme-adaptive
        // colorControlNormal/text_secondary tint and nearly disappear in dark mode.
        ivFavorite.setImageResource(
            if (recipe.isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_outline
        )
        ivFavorite.imageTintList = if (recipe.isFavorite) {
            null
        } else {
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.icon_on_photo))
        }

        ivDownload.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                if (recipe.isDownloaded) R.color.accent else R.color.icon_on_photo
            )
        )

        ingredientAdapter.submitList(recipe.ingredients)
        stepAdapter.submitList(recipe.displaySteps(isTurkish))

        val showIngredients = selectedTab == RecipeDetailTab.INGREDIENTS
        rvIngredients.isVisible = showIngredients
        rvSteps.isVisible = !showIngredients

        rowPortionEntry.isVisible = showIngredients
        tvPortionEntry.text = getString(
            R.string.portion_adjuster_entry,
            recipe.servings ?: DEFAULT_SERVINGS
        )

        rowCookModeEntry.isVisible = !showIngredients

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
