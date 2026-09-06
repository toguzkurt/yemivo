package com.tnc.yemivo.app.feature.home

import android.os.Bundle
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentHomeBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModel()

    private val adapter = RecipeAdapter(
        onItemClick = { recipe ->
            viewModel.onEvent(HomeUiEvent.RecipeClicked(recipe.id))
        },
        onFavoriteClick = { recipe ->
            viewModel.onEvent(HomeUiEvent.FavoriteClicked(recipe.id))
        }
    )

    override fun setupViews() = with(binding) {

        rvRecipes.adapter = adapter

    }

    override fun setupListeners() = with(binding) {

        searchBar.setOnClickListener {
            findNavController().navigate(R.id.search_nav_graph)
        }

        chipAll.setOnClickListener {
            viewModel.onEvent(HomeUiEvent.CuisineFilterSelected(null))
        }

        chipItalian.setOnClickListener {
            viewModel.onEvent(HomeUiEvent.CuisineFilterSelected("italian"))
        }

        chipTurkish.setOnClickListener {
            viewModel.onEvent(HomeUiEvent.CuisineFilterSelected("turkish"))
        }

        chipMexican.setOnClickListener {
            viewModel.onEvent(HomeUiEvent.CuisineFilterSelected("mexican"))
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                render(state)

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is HomeUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_home_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }
                }
            }

        }

    }

    private fun render(
        state: HomeUiState
    ) = with(binding) {

        adapter.submitList(state.recipes)
        renderChip(chipAll, state.selectedCuisine == null)
        renderChip(chipItalian, state.selectedCuisine == "italian")
        renderChip(chipTurkish, state.selectedCuisine == "turkish")
        renderChip(chipMexican, state.selectedCuisine == "mexican")

    }

    private fun renderChip(
        chip: TextView,
        isActive: Boolean
    ) {
        chip.setBackgroundResource(
            if (isActive) R.drawable.bg_chip_active else R.drawable.bg_chip_outline
        )
        chip.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                if (isActive) R.color.accent else R.color.text_secondary
            )
        )
    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
