package com.tnc.yemivo.app.feature.settings

import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.databinding.FragmentSettingsBinding
import com.tnc.core.base.BaseFragment

/**
 * Every row here (language, theme, units, notification toggles, account/subscription info,
 * privacy policy, contact, delete account) is static display content — there's no locale
 * switcher, theme switcher, or account backend anywhere in the app to back them with real state.
 * Only the screen's own entry/exit is wired.
 */
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun observeState() = Unit

}
