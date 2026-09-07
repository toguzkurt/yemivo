package com.tnc.yemivo.app.feature.profile

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.app.session.SessionPreferences
import com.tnc.yemivo.databinding.FragmentProfileBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.common.result.UiText
import com.tnc.core.extensions.showToast
import org.koin.android.ext.android.inject

/**
 * Layout-only stub for now (no ViewModel/state yet) — the only real state here is the local
 * "logged in" flag from SessionPreferences (see LoginFragment), read directly rather than via a
 * ViewModel since it's a synchronous SharedPreferences read, not an async/reactive source.
 * Profile lives inside the tabs' nested NavHost, whose NavController doesn't know about
 * login_nav_graph (a separate branch under the root graph), so we need the root NavController
 * explicitly for login, same as HomeFragment/HomeViewModel's NavigateToLogin handling in the
 * reference architecture.
 */
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val sessionPreferences: SessionPreferences by inject()

    override fun setupViews() {
        renderSession()
    }

    override fun setupListeners() = with(binding) {

        btnLogin.setOnClickListener {
            if (sessionPreferences.isLoggedIn) {
                sessionPreferences.logout()
                renderSession()
            } else {
                Navigation.findNavController(requireActivity(), R.id.navHost)
                    .navigate(R.id.login_nav_graph)
            }
        }

        premiumBanner.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

        rowSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_settings)
        }

        rowDownloads.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_downloads)
        }

        rowNotifications.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_notifications)
        }

        rowShoppingList.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_shoppingList)
        }

    }

    override fun observeState() = Unit

    private fun renderSession() = with(binding) {

        val email = sessionPreferences.userEmail

        if (sessionPreferences.isLoggedIn && email != null) {
            tvUserLabel.text = email
            ivAvatar.text = email.first().uppercaseChar().toString()
            btnLogin.text = getString(R.string.action_logout)
        } else {
            tvUserLabel.text = getString(R.string.profile_guest_user)
            ivAvatar.text = getString(R.string.profile_guest_avatar)
            btnLogin.text = getString(R.string.action_login)
        }

    }

}
