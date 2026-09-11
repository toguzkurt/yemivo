package com.tnc.yemivo.app.feature.cookmode

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.yemivo.R
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.databinding.FragmentCookModeBinding
import com.tnc.domain.recipe.model.Recipe
import com.tnc.domain.recipe.model.displaySteps
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CookModeFragment : BaseFragment<FragmentCookModeBinding>(
    FragmentCookModeBinding::inflate
) {

    private val recipeId: String
        get() = requireArguments().getString(ARG_RECIPE_ID).orEmpty()

    private val viewModel: CookModeViewModel by viewModel {
        parametersOf(recipeId)
    }

    private var lastRenderedStepIndex = -1
    private var timerJob: Job? = null

    override fun setupViews() = Unit

    override fun setupListeners() = with(binding) {

        ivClose.setOnClickListener {
            findNavController().navigateUp()
        }

        btnPrev.setOnClickListener {
            viewModel.onEvent(CookModeUiEvent.PrevClicked)
        }

        btnNext.setOnClickListener {
            val state = viewModel.state.value
            if (state.currentStepIndex == state.totalSteps - 1) {
                findNavController().navigateUp()
            } else {
                viewModel.onEvent(CookModeUiEvent.NextClicked)
            }
        }

    }

    override fun observeState() {

        launchAndRepeatWithViewLifecycle {

            viewModel.state.collect { state ->

                state.recipe?.let { render(it, state.currentStepIndex, state.totalSteps) }

            }

        }

    }

    private fun render(
        recipe: Recipe,
        currentStepIndex: Int,
        totalSteps: Int
    ) = with(binding) {

        val isTurkish = LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH
        val steps = recipe.displaySteps(isTurkish)

        if (steps.isEmpty()) return@with

        val stepNumber = currentStepIndex + 1
        val currentStep = steps[currentStepIndex]

        tvStepCount.text = getString(R.string.cook_mode_step_count_format, stepNumber, totalSteps)
        tvStepTitle.text = getString(R.string.cook_mode_step_label_format, stepNumber)
        tvStepText.text = currentStep

        renderProgress(progressRow, totalSteps, currentStepIndex)

        btnPrev.isEnabled = currentStepIndex > 0
        btnPrev.alpha = if (currentStepIndex > 0) 1f else 0.4f

        btnNext.text = if (currentStepIndex == totalSteps - 1) {
            getString(R.string.cook_mode_finish)
        } else {
            getString(R.string.cook_mode_next)
        }

        if (currentStepIndex != lastRenderedStepIndex) {
            lastRenderedStepIndex = currentStepIndex
            startTimerIfNeeded(currentStep)
        }

    }

    private fun renderProgress(
        container: LinearLayout,
        totalSteps: Int,
        currentStepIndex: Int
    ) {

        container.removeAllViews()

        val marginPx = resources.getDimensionPixelSize(R.dimen.spacing_xxs)

        repeat(totalSteps) { index ->

            val segment = View(requireContext()).apply {
                setBackgroundResource(
                    if (index <= currentStepIndex) {
                        R.drawable.bg_progress_segment_done
                    } else {
                        R.drawable.bg_progress_segment_pending
                    }
                )
            }

            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
            if (index > 0) params.marginStart = marginPx

            container.addView(segment, params)

        }

    }

    private fun startTimerIfNeeded(
        stepText: String
    ) = with(binding) {

        timerJob?.cancel()

        val durationSeconds = parseDurationSeconds(stepText)

        if (durationSeconds == null) {
            timerBox.isVisible = false
            return@with
        }

        timerBox.isVisible = true

        timerJob = viewLifecycleOwner.lifecycleScope.launch {

            var remaining = durationSeconds

            while (remaining >= 0) {
                tvTimerValue.text = formatTime(remaining)
                if (remaining == 0) break
                delay(1000)
                remaining--
            }

        }

    }

    private fun formatTime(
        totalSeconds: Int
    ): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    override fun onDestroyView() {
        timerJob?.cancel()
        super.onDestroyView()
    }

    private companion object {
        const val ARG_RECIPE_ID = "recipeId"
    }

}
