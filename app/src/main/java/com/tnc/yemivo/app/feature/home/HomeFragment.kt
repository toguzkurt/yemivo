package com.tnc.yemivo.app.feature.home

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.CuisineChipAdapter
import com.tnc.yemivo.app.feature.common.CuisineChipItem
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

    private val cuisineChipAdapter = CuisineChipAdapter(
        onChipClick = { cuisine ->
            viewModel.onEvent(HomeUiEvent.CuisineFilterSelected(cuisine))
        }
    )

    override fun setupViews() = with(binding) {

        rvRecipes.adapter = adapter
        rvCuisineChips.adapter = cuisineChipAdapter

    }

    override fun setupListeners() = with(binding) {

        searchBar.setOnClickListener {
            // Switch tabs through the BottomNavigationView's own selection mechanism (the same
            // path NavigationUI.setupWithNavController wires a real tap through), not a direct
            // findNavController().navigate(R.id.search_nav_graph) — that bypasses the nested
            // tabs NavHost's saved-state/back-stack handling entirely and left the "Ana Sayfa"
            // tab and the system back button both unable to return here.
            requireActivity()
                .findViewById<BottomNavigationView>(R.id.bottomNav)
                ?.selectedItemId = R.id.search_nav_graph
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

        cuisineChipAdapter.submitList(
            listOf(CuisineChipItem(cuisine = null, cuisineLabel = null, isSelected = state.selectedCuisine == null)) +
                state.cuisines.map {
                    CuisineChipItem(
                        cuisine = it.cuisine,
                        cuisineLabel = it.cuisineLabel,
                        isSelected = it.cuisine == state.selectedCuisine
                    )
                }
        )

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
