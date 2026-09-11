package com.tnc.yemivo.app.feature.splash

import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentSplashBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {

    private val viewModel: SplashViewModel by viewModel()

    override fun setupViews() = with(binding) {
        progressBar.max = PROGRESS_MAX
    }

    override fun setupListeners() = Unit

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
                    SplashUiEffect.NavigateToMain -> {
                        findNavController().navigate(R.id.action_splash_to_mainTabs)
                    }
                    SplashUiEffect.NavigateToOnboarding -> {
                        findNavController().navigate(R.id.action_splash_to_onboarding)
                    }
                }
            }

        }

    }

    private fun render(
        state: SplashUiState
    ) = with(binding) {

        progressBar.progress = (state.progress * PROGRESS_MAX).toInt()

    }

    private companion object {
        const val PROGRESS_MAX = 100
    }

}
