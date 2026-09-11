package com.tnc.yemivo.app.feature.onboarding

import androidx.navigation.fragment.findNavController
import com.tnc.core.base.BaseFragment
import com.tnc.yemivo.R
import com.tnc.yemivo.app.onboarding.OnboardingPreferences
import com.tnc.yemivo.databinding.FragmentOnboardingBinding
import org.koin.android.ext.android.inject

/**
 * Shown once, right after Splash on a genuine first launch (see SplashViewModel) — no
 * ViewModel/UiState here on purpose, same as LoginFragment: nothing to load, just a
 * one-way "mark it seen and move on" write.
 */
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>(
    FragmentOnboardingBinding::inflate
) {

    private val onboardingPreferences: OnboardingPreferences by inject()

    private val adapter = OnboardingPageAdapter(
        onSkipClick = { finish() },
        onContinueClick = { onContinueClicked() }
    )

    override fun setupViews() = with(binding) {
        root.adapter = adapter
        adapter.submitList(PAGES)
    }

    override fun setupListeners() = Unit

    override fun observeState() = Unit

    private fun onContinueClicked() {
        val next = binding.root.currentItem + 1
        if (next < adapter.itemCount) {
            binding.root.currentItem = next
        } else {
            finish()
        }
    }

    private fun finish() {
        onboardingPreferences.hasSeenOnboarding = true
        findNavController().navigate(R.id.action_onboarding_to_mainTabs)
    }

    private companion object {
        val PAGES = listOf(
            OnboardingPage(
                emoji = "🍲",
                titleRes = R.string.onboarding_page1_title,
                descRes = R.string.onboarding_page1_desc,
                isLast = false
            ),
            OnboardingPage(
                emoji = "📥",
                titleRes = R.string.onboarding_page2_title,
                descRes = R.string.onboarding_page2_desc,
                isLast = false
            ),
            OnboardingPage(
                emoji = "🛒",
                titleRes = R.string.onboarding_page3_title,
                descRes = R.string.onboarding_page3_desc,
                isLast = true
            )
        )
    }

}
