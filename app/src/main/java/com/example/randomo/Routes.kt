package com.example.randomo

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object PoliceListItemScreen: Route

    @Serializable
    data class PoliceDetailScreen(val id: String, val name: String): Route

    @Serializable
    data object HomeScreen: Route
}