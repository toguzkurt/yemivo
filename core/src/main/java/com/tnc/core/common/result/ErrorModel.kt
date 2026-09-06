package com.tnc.core.common.result

data class ErrorModel(
    val type: ErrorType,
    val message: UiText,
    val code: Int? = null,
    val throwable: Throwable? = null
)
