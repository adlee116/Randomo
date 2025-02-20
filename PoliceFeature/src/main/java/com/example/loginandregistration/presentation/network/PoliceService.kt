package com.example.loginandregistration.presentation.network

import com.example.loginandregistration.presentation.domain.PoliceListItem
import retrofit2.Response
import retrofit2.http.GET

interface PoliceService {

    @GET("api/forces")
    suspend fun getForcesList(): Response<List<PoliceListItem>>

}