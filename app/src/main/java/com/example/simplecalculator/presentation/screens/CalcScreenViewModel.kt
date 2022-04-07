package com.example.simplecalculator.presentation.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.common.Resource
import com.example.simplecalculator.domain.usecase.GetConversionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CalcScreenViewModel @Inject constructor(
    private val getConversionUseCase: GetConversionUseCase,
) : ViewModel() {

    private val _state = mutableStateOf("")
    val state: State<String> = _state

    fun getConversion(baseC: String, targetC: String, amountC: String) {
        getConversionUseCase(baseC, targetC, amountC).onEach { result ->
            when(result) {
                is Resource.Success -> _state.value = result.data!!.conversionResult.toString()
                is Resource.Error -> _state.value = result.message.toString()
                is Resource.Loading -> _state.value = ""
            }
        }.launchIn(viewModelScope)
    }

}