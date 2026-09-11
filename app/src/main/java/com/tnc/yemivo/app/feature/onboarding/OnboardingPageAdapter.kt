package com.tnc.yemivo.app.feature.onboarding

import androidx.recyclerview.widget.DiffUtil
import com.tnc.core.base.BaseAdapter
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentOnboardingPageBinding

data class OnboardingPage(
    val emoji: String,
    val titleRes: Int,
    val descRes: Int,
    val isLast: Boolean
)

class OnboardingPageAdapter(
    private val onSkipClick: () -> Unit,
    private val onContinueClick: () -> Unit
) : BaseAdapter<OnboardingPage, FragmentOnboardingPageBinding>(
    bindingInflater = FragmentOnboardingPageBinding::inflate,
    diffCallback = object : DiffUtil.ItemCallback<OnboardingPage>() {

        override fun areItemsTheSame(
            oldItem: OnboardingPage,
            newItem: OnboardingPage
        ) = oldItem.titleRes == newItem.titleRes

        override fun areContentsTheSame(
            oldItem: OnboardingPage,
            newItem: OnboardingPage
        ) = oldItem == newItem

    }
) {

    override fun onBind(
        binding: FragmentOnboardingPageBinding,
        item: OnboardingPage,
        position: Int
    ) = with(binding) {

        tvIllustration.text = item.emoji
        tvTitle.setText(item.titleRes)
        tvDescription.setText(item.descRes)
        btnContinue.setText(
            if (item.isLast) R.string.onboarding_get_started else R.string.onboarding_continue
        )

        listOf(dot1, dot2, dot3).forEachIndexed { index, dot ->
            val isActive = index == position
            dot.setBackgroundResource(
                if (isActive) R.drawable.bg_dot_active else R.drawable.bg_dot_inactive
            )
            dot.layoutParams = dot.layoutParams.apply {
                width = dot.resources.getDimensionPixelSize(
                    if (isActive) R.dimen.dot_indicator_active_width else R.dimen.dot_indicator_size
                )
            }
        }

        tvSkip.setOnClickListener { onSkipClick() }
        btnContinue.setOnClickListener { onContinueClick() }

    }

}
