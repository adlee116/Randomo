package com.example.loginandregistration.presentation.policeItemListScreen

import com.example.loginandregistration.presentation.domain.PoliceListItem

data class PoliceListState(val state: State) {
    sealed class State {
        data object Loading: State()
        class Reading(val listItems: List<PoliceListItem>): State()
        class Selected(val id: String, val name: String): State()
    }
}

// Loading, ReadingState(ListItems), SelectedState(id: String, name: String)
