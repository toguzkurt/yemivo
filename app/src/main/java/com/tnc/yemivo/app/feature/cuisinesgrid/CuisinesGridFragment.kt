package com.tnc.yemivo.app.feature.cuisinesgrid

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentCuisinesGridBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CuisinesGridFragment : BaseFragment<FragmentCuisinesGridBinding>(
    FragmentCuisinesGridBinding::inflate
) {

    private val viewModel: CuisinesGridViewModel by viewModel()

    private val adapter = CuisinesGridAdapter(
        onItemClick = { cuisine ->
            viewModel.onEvent(
                CuisinesGridUiEvent.CuisineClicked(cuisine.cuisine, cuisine.cuisineLabel)
            )
        }
    )

    override fun setupViews() = with(binding) {
        rvCuisines.adapter = adapter
    }

    override fun setupListeners() = with(binding) {
        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                adapter.submitList(state.cuisines)

            }

        }

    }

    override fun observeEffect() {

        launchAndRepeatWithViewLifecycle {

            viewModel.effect.collect { effect ->
                when (effect) {
                    is CuisinesGridUiEffect.NavigateToCuisineRecipes -> {
                        findNavController().navigate(
                            R.id.action_cuisinesGrid_to_cuisineRecipes,
                            Bundle().apply {
                                putString(ARG_CUISINE, effect.cuisine)
                                putString(ARG_CUISINE_LABEL, effect.cuisineLabel)
                            }
                        )
                    }
                }
            }

        }

    }

    private companion object {
        const val ARG_CUISINE = "cuisine"
        const val ARG_CUISINE_LABEL = "cuisineLabel"
    }

}
