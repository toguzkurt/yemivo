package com.tnc.yemivo.app.feature.login

import androidx.navigation.fragment.findNavController
import com.tnc.yemivo.R
import com.tnc.yemivo.databinding.FragmentLoginBinding
import com.tnc.core.base.BaseFragment
import com.tnc.core.common.result.UiText
import com.tnc.core.extensions.showToast

/**
 * There's no real auth backend anywhere in the app (domain/data have no user/session concept at
 * all), so "login" here is local field validation only, then the same tabs destination a guest
 * already sees — action_login_to_home pops this whole branch off the root graph either way.
 * Google/Apple/register are dead ends until that backend exists, so they say so rather than
 * silently doing nothing.
 */
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    override fun setupViews() = Unit

    override fun setupListeners() = with(binding) {

        btnLogin.setOnClickListener {
            val email = etEmail.text?.toString().orEmpty()
            val password = etPassword.text?.toString().orEmpty()

            if (email.isBlank() || password.isBlank()) {
                requireContext().showToast(UiText.StringResource(R.string.login_error_required))
            } else {
                findNavController().navigate(R.id.action_login_to_home)
            }
        }

        btnGoogle.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

        btnApple.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

        btnRegister.setOnClickListener {
            requireContext().showToast(UiText.StringResource(R.string.coming_soon))
        }

    }

    override fun observeState() = Unit

}
