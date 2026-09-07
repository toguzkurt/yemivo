package com.tnc.yemivo.app.feature.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import android.widget.TextView
import com.tnc.yemivo.R
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

    override fun setupViews() = with(binding) {

        rvResults.adapter = adapter

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

        chipTurkish.setOnClickListener {
            toggleCuisine("turkish")
        }

        chipItalian.setOnClickListener {
            toggleCuisine("italian")
        }

        chipMexican.setOnClickListener {
            toggleCuisine("mexican")
        }

        chipIndian.setOnClickListener {
            toggleCuisine("indian")
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

    private fun toggleCuisine(
        cuisine: String
    ) {
        val current = viewModel.state.value.selectedCuisine
        viewModel.onEvent(
            SearchUiEvent.CuisineFilterSelected(if (current == cuisine) null else cuisine)
        )
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

        renderChip(chipTurkish, state.selectedCuisine == "turkish")
        renderChip(chipItalian, state.selectedCuisine == "italian")
        renderChip(chipMexican, state.selectedCuisine == "mexican")
        renderChip(chipIndian, state.selectedCuisine == "indian")

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
