package com.tnc.core.common.result

sealed interface Result<out T> {

    data class Success<T>(
        val data: T
    ) : Result<T>

    data class Error(
        val error: ErrorModel
    ) : Result<Nothing>
}
