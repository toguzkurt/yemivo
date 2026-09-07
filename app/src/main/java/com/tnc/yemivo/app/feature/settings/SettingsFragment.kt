package com.tnc.yemivo.app.feature.settings

import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tnc.yemivo.R
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.app.theme.ThemeMode
import com.tnc.yemivo.app.theme.ThemePreferences
import com.tnc.yemivo.databinding.FragmentSettingsBinding
import com.tnc.core.base.BaseFragment
import org.koin.android.ext.android.inject

/**
 * Language and theme are real (see LocaleHelper/ThemePreferences) — everything else here (units,
 * notification toggles, account/subscription info, privacy policy, contact, delete account)
 * stays static display content, since making those real needs an actual account/preferences
 * backend this app doesn't have.
 */
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private val themePreferences: ThemePreferences by inject()

    override fun setupViews() {
        renderLanguageValue()
        renderThemeValue()
    }

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        rowLanguage.setOnClickListener {
            showLanguagePicker()
        }

        rowTheme.setOnClickListener {
            showThemePicker()
        }

    }

    override fun observeState() = Unit

    private fun showLanguagePicker() {

        val tags = listOf(LocaleHelper.TAG_TURKISH, LocaleHelper.TAG_ENGLISH)
        val labels = arrayOf("Türkçe", "English")
        val currentIndex = tags.indexOf(LocaleHelper.currentTag(requireContext())).coerceAtLeast(0)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.settings_language)
            .setSingleChoiceItems(labels, currentIndex) { dialog, which ->
                LocaleHelper.setLocale(requireContext(), tags[which])
                dialog.dismiss()
            }
            .show()

    }

    private fun showThemePicker() {

        val modes = ThemeMode.entries
        val labels = resources.getStringArray(R.array.theme_mode_options)
        val currentIndex = modes.indexOf(themePreferences.mode).coerceAtLeast(0)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.settings_theme)
            .setSingleChoiceItems(labels, currentIndex) { dialog, which ->
                themePreferences.mode = modes[which]
                renderThemeValue()
                dialog.dismiss()
            }
            .show()

    }

    private fun renderLanguageValue() {
        binding.tvLanguageValue.text = if (LocaleHelper.currentTag(requireContext()) == LocaleHelper.TAG_TURKISH) {
            "Türkçe"
        } else {
            "English"
        }
    }

    private fun renderThemeValue() {
        val labels = resources.getStringArray(R.array.theme_mode_options)
        binding.tvThemeValue.text = labels[ThemeMode.entries.indexOf(themePreferences.mode)]
    }

}
