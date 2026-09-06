package com.tnc.yemivo.app.feature.login

import com.tnc.yemivo.databinding.FragmentLoginBinding
import com.tnc.core.base.BaseFragment

/**
 * Layout-only stub for now: the mockup's email/password fields are still static TextViews, not
 * EditTexts, so there's no real form state to back a LoginContract/ViewModel yet.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = Unit

    override fun observeState() = Unit

}
