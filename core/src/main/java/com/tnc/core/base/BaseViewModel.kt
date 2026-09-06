package com.tnc.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tnc.core.dispatcher.DefaultDispatcherProvider
import com.tnc.core.dispatcher.DispatcherProvider
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE : Any, EFFECT : Any>(
    initialState: STATE,
    private val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EFFECT>(extraBufferCapacity = 1)
    val effect: SharedFlow<EFFECT> = _effect.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val jobs = mutableMapOf<String, Job>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable !is CancellationException) {
            _isLoading.value = false
            onError(throwable)
        }
    }

    protected fun setState(
        reducer: STATE.() -> STATE
    ) {
        _state.update { currentState -> currentState.reducer() }
    }

    protected fun sendEffect(
        effect: EFFECT
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    /**
     * [key] identifies this operation's job. Launching under a key that is already running
     * cancels the previous job for that key when [cancelPrevious] is true, without touching
     * jobs running under other keys.
     */
    protected fun launch(
        key: String = DEFAULT_JOB_KEY,
        showLoading: Boolean = false,
        cancelPrevious: Boolean = false,
        block: suspend () -> Unit
    ): Job {
        if (cancelPrevious) {
            jobs[key]?.cancel()
        }

        val job = viewModelScope.launch(
            dispatcherProvider.main + exceptionHandler
        ) {
            try {
                if (showLoading) {
                    _isLoading.value = true
                }
                block()
            } finally {
                if (showLoading) {
                    _isLoading.value = false
                }
                jobs.remove(key)
            }
        }

        jobs[key] = job

        return job
    }

    protected fun cancelJob(
        key: String = DEFAULT_JOB_KEY
    ) {
        jobs.remove(key)?.cancel()
    }

    protected fun cancelAllJobs() {
        jobs.values.forEach { it.cancel() }
        jobs.clear()
    }

    protected open fun onError(
        throwable: Throwable
    ) = Unit

    override fun onCleared() {
        cancelAllJobs()
        super.onCleared()
    }

    private companion object {
        const val DEFAULT_JOB_KEY = "default"
    }
}
