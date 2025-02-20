package com.example.loginandregistration.presentation.policeDetailScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(): ViewModel() {

    fun getRandomString(): String {
        return "REGI"
    }
}