package com.example.loginandregistration.presentation.policeItemListScreen

sealed class PoliceListEvent {
    data object GetList: PoliceListEvent()
    data class ClickItem(val id: String, val name: String): PoliceListEvent()
}