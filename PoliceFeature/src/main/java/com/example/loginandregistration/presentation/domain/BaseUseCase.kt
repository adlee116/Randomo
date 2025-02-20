package com.example.loginandregistration.presentation.domain

import com.example.loginandregistration.presentation.domain.errors.DomainError
import kotlinx.coroutines.*

abstract class BaseUseCase<out Type, in Params> where Type : Any? {
    var enableTesting: Boolean = false
    abstract suspend fun run(params: Params): Result<Type, DomainError>
    open operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Result<Type, DomainError>) -> Unit = {}) {
        scope.launch {
            val job =
                if (enableTesting) withContext(scope.coroutineContext) { run(params) }
                else withContext(Dispatchers.IO) { run(params) }
            withContext(scope.coroutineContext) { onResult(job) }
        }
    }
}
