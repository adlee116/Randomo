package com.example.loginandregistration.presentation.network

import com.example.loginandregistration.presentation.domain.PoliceListItem
import javax.inject.Inject

class PoliceRepo @Inject constructor(private val policeService: PoliceService) {

    suspend fun getPoliceForcesList(): List<PoliceListItem>? {
        val response = policeService.getForcesList()
        return response.body()
    }
}