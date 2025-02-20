package com.example.loginandregistration.presentation.policeItemListScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginandregistration.presentation.domain.GetPoliceListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoliceListScreenViewModel @Inject constructor(
    val getPoliceListUseCase: GetPoliceListUseCase
): ViewModel() {

    private val _policeState = MutableStateFlow<PoliceListState?>(null)
    val policeState = _policeState.asStateFlow()

    private val _failures: MutableStateFlow<String?> = MutableStateFlow(null)
    val failures: StateFlow<String?> get() = _failures.asStateFlow()

    init {
        _policeState.value = PoliceListState(PoliceListState.State.Loading)
        process(PoliceListEvent.GetList)
    }

    fun process(event: PoliceListEvent) {
        when(event) {
            is PoliceListEvent.ClickItem -> itemSelected(event)
            is PoliceListEvent.GetList -> getList()
        }
    }

    private fun itemSelected(event: PoliceListEvent.ClickItem) {
        _policeState.value = PoliceListState(PoliceListState.State.Selected(event.id, event.name))
    }

    private fun getList() {
        getPoliceListUseCase.invoke(viewModelScope, Unit) { result ->
            result.result(
                onSuccess = {
                    _policeState.value = PoliceListState(PoliceListState.State.Reading(it))
                },
                onFailure = {
                    viewModelScope.launch { _failures.emit(it.messageString()) }
                }
            )
        }
    }


    // Event

    // LIVE DATA FOR STATE

}