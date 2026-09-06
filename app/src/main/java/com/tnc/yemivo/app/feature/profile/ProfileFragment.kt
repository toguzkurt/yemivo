package com.tnc.yemivo.app.feature.profile

import androidx.navigation.Navigation
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentProfileBinding
import com.tnc.core.base.BaseFragment

/**
 * Layout-only stub for now (no ViewModel/state yet) — only the "Giriş yap" action is wired,
 * since it's a pure navigation action with no state of its own. Profile lives inside the tabs'
 * nested NavHost, whose NavController doesn't know about login_nav_graph (a separate branch
 * under the root graph), so we need the root NavController explicitly, same as
 * HomeFragment/HomeViewModel's NavigateToLogin handling in the reference architecture.
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

    }

    override fun observeState() = Unit

}
