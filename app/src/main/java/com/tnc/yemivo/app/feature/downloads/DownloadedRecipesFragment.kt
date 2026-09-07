package com.tnc.yemivo.app.feature.downloads

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.home.RecipeAdapter
import com.tnc.yemivo.databinding.FragmentDownloadedRecipesBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DownloadedRecipesFragment : BaseFragment<FragmentDownloadedRecipesBinding>(
    FragmentDownloadedRecipesBinding::inflate
) {

    private val viewModel: DownloadedRecipesViewModel by viewModel()

    private val adapter = RecipeAdapter(
        onItemClick = { recipe ->
            viewModel.onEvent(DownloadedRecipesUiEvent.RecipeClicked(recipe.id))
        },
        onFavoriteClick = { recipe ->
            viewModel.onEvent(DownloadedRecipesUiEvent.FavoriteClicked(recipe.id))
        }
    )

    override fun setupViews() = with(binding) {

        rvDownloads.adapter = adapter

    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnExplore.setOnClickListener {
            viewModel.onEvent(DownloadedRecipesUiEvent.ExploreClicked)
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

                    is DownloadedRecipesUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_downloads_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }

                    DownloadedRecipesUiEffect.NavigateToHomeTab -> {
                        requireActivity()
                            .findViewById<BottomNavigationView>(R.id.bottomNav)
                            ?.selectedItemId = R.id.home_nav_graph
                    }

                }
            }

        }

    }

    private fun render(
        state: DownloadedRecipesUiState
    ) = with(binding) {

        val hasDownloads = state.recipes.isNotEmpty()

        adapter.submitList(state.recipes)

        rvDownloads.isVisible = hasDownloads
        emptyState.isVisible = !hasDownloads

    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
