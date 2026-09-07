package com.tnc.yemivo.app.feature.favorites

import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.home.RecipeAdapter
import com.tnc.yemivo.databinding.FragmentFavoritesBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(
    FragmentFavoritesBinding::inflate
) {

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter = RecipeAdapter(
        onItemClick = { recipe ->
            viewModel.onEvent(FavoritesUiEvent.RecipeClicked(recipe.id))
        },
        onFavoriteClick = { recipe ->
            viewModel.onEvent(FavoritesUiEvent.FavoriteClicked(recipe.id))
        }
    )

    override fun setupViews() = with(binding) {

        rvFavorites.adapter = adapter

    }

    override fun setupListeners() = with(binding) {

        btnExplore.setOnClickListener {
            viewModel.onEvent(FavoritesUiEvent.ExploreClicked)
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

                    is FavoritesUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_favorites_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }

                    FavoritesUiEffect.NavigateToHomeTab -> {
                        requireActivity()
                            .findViewById<BottomNavigationView>(R.id.bottomNav)
                            ?.selectedItemId = R.id.home_nav_graph
                    }

                }
            }

        }

    }

    private fun render(
        state: FavoritesUiState
    ) = with(binding) {

        val count = state.favorites.size
        val hasFavorites = count > 0

        tvProgress.isVisible = hasFavorites
        progressTrack.isVisible = hasFavorites
        rvFavorites.isVisible = hasFavorites
        limitBox.isVisible = hasFavorites
        emptyState.isVisible = !hasFavorites

        if (hasFavorites) {

            adapter.submitList(state.favorites)

            tvProgress.text = getString(
                R.string.favorites_progress,
                count.coerceAtMost(FREE_FAVORITES_LIMIT),
                FREE_FAVORITES_LIMIT
            )

            val filledWeight = count.coerceIn(0, FREE_FAVORITES_LIMIT)
            (progressFill.layoutParams as LinearLayout.LayoutParams).weight = filledWeight.toFloat()
            (progressRemainder.layoutParams as LinearLayout.LayoutParams).weight =
                (FREE_FAVORITES_LIMIT - filledWeight).toFloat()
            progressTrack.requestLayout()

            tvRemaining.text = getString(
                R.string.favorites_remaining,
                (FREE_FAVORITES_LIMIT - count).coerceAtLeast(0)
            )

        }

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
        const val FREE_FAVORITES_LIMIT = 5
    }

}
