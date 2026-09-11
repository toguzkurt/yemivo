package com.tnc.yemivo.app.feature.surpriserecipe

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displayName
import com.tnc.yemivo.R
import com.tnc.yemivo.app.feature.common.bindRecipeImage
import com.tnc.yemivo.app.feature.common.detailSummary
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.FragmentSurpriseRecipeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SurpriseRecipeFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSurpriseRecipeBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel: SurpriseRecipeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurpriseRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Lets the sheet's own rounded-top drawable show through instead of the framework's
        // opaque square-cornered default background.
        (dialog as? BottomSheetDialog)
            ?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            ?.setBackgroundColor(Color.TRANSPARENT)

        setupListeners()
        observeState()
        observeEffect()
    }

    private fun setupListeners() = with(binding) {

        ivClose.setOnClickListener {
            dismiss()
        }

        btnReroll.setOnClickListener {
            viewModel.onEvent(SurpriseRecipeUiEvent.RerollClicked)
        }

        btnViewRecipe.setOnClickListener {
            viewModel.onEvent(SurpriseRecipeUiEvent.ViewRecipeClicked)
        }

    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    state.recipe?.let { render(it) }

                }

            }

        }

    }

    private fun observeEffect() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.effect.collect { effect ->
                    when (effect) {
                        is SurpriseRecipeUiEffect.NavigateToRecipeDetail -> {
                            findNavController().navigate(
                                R.id.action_surpriseRecipe_to_recipeDetail,
                                Bundle().apply { putString(ARG_RECIPE_ID, effect.recipeId) }
                            )
                        }
                    }
                }

            }

        }

    }

    private fun render(
        recipe: Recipe
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH

        tvName.text = recipe.displayName(isTurkish)
        tvMeta.text = recipe.detailSummary(isTurkish)
        ivHero.bindRecipeImage(
            recipe = recipe,
            iconPadding = resources.getDimensionPixelSize(R.dimen.spacing_huge)
        )
        confettiView.burst()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
