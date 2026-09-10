package com.tnc.yemivo.app.feature.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.CuisineChipAdapter
import com.tnc.yemivo.app.feature.common.CuisineChipItem
import com.tnc.yemivo.databinding.FragmentSearchBinding
import com.tnc.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    private val viewModel: SearchViewModel by viewModel()

    private val adapter = SearchResultAdapter(
        onItemClick = { recipe ->
            viewModel.onEvent(SearchUiEvent.RecipeClicked(recipe.id))
        }
    )

    private val cuisineChipAdapter = CuisineChipAdapter(
        onChipClick = { cuisine ->
            viewModel.onEvent(SearchUiEvent.CuisineFilterSelected(cuisine))
        }
    )

    override fun setupViews() = with(binding) {

        rvResults.adapter = adapter
        rvCuisineChips.adapter = cuisineChipAdapter

    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        etQuery.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.onEvent(SearchUiEvent.QueryChanged(s?.toString().orEmpty()))
            }

            override fun afterTextChanged(s: Editable?) = Unit

        })

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
                    is SearchUiEffect.NavigateToRecipeDetail -> {
                        findNavController().navigate(
                            R.id.action_search_to_recipeDetail,
                            Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                        )
                    }
                }
            }

        }

    }

    private fun render(
        state: SearchUiState
    ) = with(binding) {

        adapter.submitList(state.results)

        tvResultCount.text = if (state.results.isEmpty()) {
            getString(R.string.search_result_count_empty)
        } else {
            getString(R.string.search_result_count, state.results.size)
        }

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
