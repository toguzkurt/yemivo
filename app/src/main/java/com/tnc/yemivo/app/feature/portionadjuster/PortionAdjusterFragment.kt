package com.tnc.yemivo.app.feature.portionadjuster

import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.core.extensions.showToast
import com.tnc.yemivo.R
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.FragmentPortionAdjusterBinding
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displayName
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PortionAdjusterFragment : BaseFragment<FragmentPortionAdjusterBinding>(
    FragmentPortionAdjusterBinding::inflate
) {

    private val recipeId: String
        get() = requireArguments().getString(ARG_RECIPE_ID).orEmpty()

    private val viewModel: PortionAdjusterViewModel by viewModel {
        parametersOf(recipeId)
    }

    private val adapter = PortionIngredientAdapter()

    override fun setupViews() = with(binding) {
        rvIngredients.adapter = adapter
    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnDecrease.setOnClickListener {
            viewModel.onEvent(PortionAdjusterUiEvent.DecrementClicked)
        }

        btnIncrease.setOnClickListener {
            viewModel.onEvent(PortionAdjusterUiEvent.IncrementClicked)
        }

        btnAddToShoppingList.setOnClickListener {
            viewModel.onEvent(PortionAdjusterUiEvent.AddToShoppingListClicked)
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                state.recipe?.let { render(it, state.currentServings) }

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is PortionAdjusterUiEffect.ShowMessage -> {
                        requireContext().showToast(effect.message)
                    }
                }
            }

        }

    }

    private fun render(
        recipe: Recipe,
        currentServings: Int
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH
        val originalServings = recipe.servings ?: DEFAULT_SERVINGS

        tvTitle.text = getString(R.string.portion_adjuster_title, recipe.displayName(isTurkish))
        tvPortionCount.text = getString(R.string.portion_adjuster_servings_count, currentServings)
        tvIngredientsLabel.text = getString(R.string.portion_adjuster_section_label, currentServings)

        adapter.submitList(
            recipe.ingredients.map { it.scaledFor(originalServings, currentServings, isTurkish) }
        )

        val isScaled = currentServings != originalServings
        tvHint.isVisible = isScaled
        tvHint.text = getString(R.string.portion_adjuster_hint, originalServings)

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
