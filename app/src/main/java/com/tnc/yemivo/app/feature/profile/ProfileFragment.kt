package com.tnc.yemivo.app.feature.profile

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentProfileBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.common.result.UiText
import com.tnc.core.extensions.showToast

/**
 * Layout-only stub for now (no ViewModel/state yet) — only navigation actions are wired, since
 * this screen has no state of its own (no real account/downloads/notifications backend exists
 * anywhere in the app yet). Profile lives inside the tabs' nested NavHost, whose NavController
 * doesn't know about login_nav_graph (a separate branch under the root graph), so we need the
 * root NavController explicitly for login, same as HomeFragment/HomeViewModel's NavigateToLogin
 * handling in the reference architecture.
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = with(binding) {

        btnLogin.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHost)
                .navigate(R.id.login_nav_graph)
        }

        rowSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_settings)
        }

        rowDownloads.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

        rowNotifications.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

        rowShoppingList.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_shoppingList)
        }

    }

    override fun observeState() = Unit

}
