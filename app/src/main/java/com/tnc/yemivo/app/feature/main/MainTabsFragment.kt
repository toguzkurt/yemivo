package com.tnc.yemivo.app.feature.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentMainTabsBinding
import com.tnc.core.base.BaseFragment

/**
 * Hosts its own nested NavHostFragment + BottomNavigationView (tabs_nav_graph). No ViewModel/
 * UiState here on purpose: "which tab is selected" is navigation framework state already owned
 * by the nested NavController, not app business state, so this doesn't follow the MVI Contract
 * pattern every other screen uses — it still extends BaseFragment for the free edge-to-edge
 * inset handling and consistent lifecycle wiring.
 */
class MainTabsFragment : BaseFragment<FragmentMainTabsBinding>(
    FragmentMainTabsBinding::inflate
) {

    override fun setupViews() = with(binding) {

        val tabsNavHost = childFragmentManager
            .findFragmentById(R.id.tabsNavHost) as NavHostFragment

        bottomNav.setupWithNavController(tabsNavHost.navController)

    }

    override fun setupListeners() = Unit

    override fun observeState() = Unit

}
