package com.tnc.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseFragment<VB : ViewBinding>(
    private val bindingInflater: (
        LayoutInflater,
        ViewGroup?,
        Boolean
    ) -> VB
) : Fragment() {

    private var _binding: VB? = null

    protected val binding: VB
        get() = checkNotNull(_binding) {
            "Binding is only valid between onCreateView and onDestroyView."
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = bindingInflater(
            inflater,
            container,
            false
        )

        binding.root.applySystemBarInsetsPadding()

        return binding.root
    }

    @CallSuper
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupListeners()
        observeState()
        observeEffect()
    }

    /**
     * UI initialization.
     */
    protected abstract fun setupViews()

    /**
     * Click listeners, TextWatchers etc.
     */
    protected abstract fun setupListeners()

    /**
     * Observe StateFlow.
     */
    protected abstract fun observeState()

    /**
     * Observe one-shot events (Navigation, Snackbar, Dialog...)
     */
    protected open fun observeEffect() = Unit

    /**
     * Lifecycle aware coroutine launcher.
     */
    protected fun launchAndRepeatWithViewLifecycle(
        minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                block()
            }
        }
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
