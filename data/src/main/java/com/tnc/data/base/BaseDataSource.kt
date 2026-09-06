package com.tnc.data.base

import com.tnc.core.common.result.ErrorModel
import com.tnc.core.common.result.ErrorType
import com.tnc.core.common.result.Result
import com.tnc.core.common.result.UiText
import kotlinx.coroutines.CancellationException

/**
 * Wraps a suspending data-layer call so every repository/data source reports failures the same
 * way (a [Result.Error] with an [ErrorModel]) instead of letting exceptions leak upward.
 */
abstract class BaseDataSource {

    protected suspend fun <T> safeCall(
        block: suspend () -> T
    ): Result<T> {
        return try {
            Result.Success(block())
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (throwable: Throwable) {
            Result.Error(
                ErrorModel(
                    type = ErrorType.UNKNOWN,
                    message = UiText.DynamicString(throwable.message.orEmpty()),
                    throwable = throwable
                )
            )
        }
    }
}
