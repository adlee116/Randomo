package com.example.loginandregistration.presentation.domain

import com.example.loginandregistration.presentation.domain.errors.DomainError

sealed class Result<out S, out F : DomainError> {
  data class Success<out S>(val successType: S) : Result<S, Nothing>()
  open class Failure(val ex: DomainError) : Result<Nothing, DomainError>()
  fun result(
    onSuccess: (S) -> Unit = {},
    onFailure: (DomainError) -> Unit = {}
  ): Unit = when (this) {
    is Success -> onSuccess(successType)
    is Failure -> onFailure(ex)
  }
}