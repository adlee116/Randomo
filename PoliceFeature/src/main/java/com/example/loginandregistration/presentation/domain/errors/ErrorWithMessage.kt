package com.example.loginandregistration.presentation.domain.errors

import com.example.loginandregistration.presentation.domain.errors.DomainError

data class ErrorWithMessage(
  val messageString: String? = null,
  val messageInt: Int? = null
) : DomainError {

  override fun messageString(): String? = messageString
  override fun messageInt(): Int? = messageInt

}

