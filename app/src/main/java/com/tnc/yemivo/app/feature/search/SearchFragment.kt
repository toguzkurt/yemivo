package com.tnc.yemivo.app.feature.search

import com.tnc.yemivo.databinding.FragmentSearchBinding
import com.tnc.core.base.BaseFragment

/**
 * Layout-only stub for now: the mockup's search field/filter chips still need to become real
 * inputs (EditText + selectable chip state) before this gets its own MVI Contract/ViewModel,
 * same pattern as [com.tnc.yemivo.app.feature.home.HomeFragment].
 */
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    FragmentSearchBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = with(binding) {

        ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun observeState() = Unit

}
