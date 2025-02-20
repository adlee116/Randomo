package com.example.loginandregistration.presentation.domain.errors

interface DomainError {
  fun messageString(): String? = null

  fun messageInt(): Int? = null
}