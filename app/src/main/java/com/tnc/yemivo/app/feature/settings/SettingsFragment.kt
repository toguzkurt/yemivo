package com.tnc.yemivo.app.feature.settings

import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tnc.yemivo.R
import com.tnc.yemivo.app.session.SessionPreferences
import com.tnc.yemivo.app.theme.LocaleHelper
import com.tnc.yemivo.app.theme.NotificationPreferences
import com.tnc.yemivo.app.theme.ThemeMode
import com.tnc.yemivo.app.theme.ThemePreferences
import com.tnc.yemivo.app.theme.UnitPreferences
import com.tnc.yemivo.app.theme.UnitSystem
import com.tnc.yemivo.databinding.FragmentSettingsBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.common.result.UiText
import com.tnc.core.extensions.showToast
import org.koin.android.ext.android.inject

/**
 * Language, theme, unit system, notification toggles, and the account row are all real (see
 * LocaleHelper/ThemePreferences/UnitPreferences/NotificationPreferences/SessionPreferences).
 * Privacy policy, contact, and delete account stay static — those need real legal copy and a
 * real account backend respectively, well beyond a preferences screen. Unit system only persists
 * the choice — recipe amounts are free-text from TheMealDB ("1 cup", "500g", "a pinch", ...),
 * not structured quantity+unit pairs, so there's no safe way to actually convert displayed
 * amounts without risking a wrong parse turning into a wrong ingredient quantity. Subscription
 * management stays a "coming soon" tap — there's no real payment/subscription system anywhere in
 * the app, so its value now honestly reads "Ücretsiz"/"Free" instead of the mockup's fake
 * "Premium" claim about the user's own account.
 */
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {

    private val themePreferences: ThemePreferences by inject()

    private val unitPreferences: UnitPreferences by inject()

    private val notificationPreferences: NotificationPreferences by inject()

    private val sessionPreferences: SessionPreferences by inject()

    override fun setupViews() {
        renderLanguageValue()
        renderThemeValue()
        renderUnitValue()
        renderAccountValue()
        renderToggle(binding.toggleDailyRecipe, notificationPreferences.isDailyRecipeEnabled)
        renderToggle(binding.toggleCampaigns, notificationPreferences.isCampaignsEnabled)
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

        rowUnit.setOnClickListener {
            showUnitPicker()
        }

        rowNotifDaily.setOnClickListener {
            notificationPreferences.isDailyRecipeEnabled = !notificationPreferences.isDailyRecipeEnabled
            renderToggle(toggleDailyRecipe, notificationPreferences.isDailyRecipeEnabled)
        }

        rowNotifCampaigns.setOnClickListener {
            notificationPreferences.isCampaignsEnabled = !notificationPreferences.isCampaignsEnabled
            renderToggle(toggleCampaigns, notificationPreferences.isCampaignsEnabled)
        }

        rowAccount.setOnClickListener {
            if (!sessionPreferences.isLoggedIn) {
                Navigation.findNavController(requireActivity(), R.id.navHost)
                    .navigate(R.id.login_nav_graph)
            }
        }

        rowSubscription.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
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

    private fun showUnitPicker() {

        val systems = UnitSystem.entries
        val labels = resources.getStringArray(R.array.unit_system_options)
        val currentIndex = systems.indexOf(unitPreferences.system).coerceAtLeast(0)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.settings_unit)
            .setSingleChoiceItems(labels, currentIndex) { dialog, which ->
                unitPreferences.system = systems[which]
                renderUnitValue()
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

    private fun renderUnitValue() {
        val labels = resources.getStringArray(R.array.unit_system_options)
        binding.tvUnitValue.text = labels[UnitSystem.entries.indexOf(unitPreferences.system)]
    }

    private fun renderAccountValue() {
        binding.tvAccountValue.text = sessionPreferences.userEmail?.takeIf { sessionPreferences.isLoggedIn }
            ?: getString(R.string.profile_guest_user)
    }

    private fun renderToggle(
        toggle: View,
        isOn: Boolean
    ) {
        toggle.setBackgroundResource(
            if (isOn) R.drawable.bg_toggle_on else R.drawable.bg_toggle_off
        )
    }

}
