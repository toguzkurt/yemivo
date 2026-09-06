package com.tnc.domain.common.base

import com.tnc.core.common.result.Result

abstract class BaseUseCase<in PARAMS, RESULT> {

    suspend operator fun invoke(params: PARAMS): Result<RESULT> {
        return execute(params)
    }

    protected abstract suspend fun execute(params: PARAMS): Result<RESULT>
}
