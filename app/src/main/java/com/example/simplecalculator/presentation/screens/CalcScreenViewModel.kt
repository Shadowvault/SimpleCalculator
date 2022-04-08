package com.example.simplecalculator.presentation.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplecalculator.common.Resource
import com.example.simplecalculator.domain.usecase.GetConversionUseCase
import com.github.keelar.exprk.Expressions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalcScreenViewModel @Inject constructor(
    private val getConversionUseCase: GetConversionUseCase,
) : ViewModel() {

    private val _conversionResult = mutableStateOf("")
    val conversion: State<String> = _conversionResult

    private val _hasBeenEval = mutableStateOf(false)

    private val _mainTextFlow = mutableStateOf<String>("")
    val mainTextState = _mainTextFlow

    private val _historyTextFlow = mutableStateOf<String>("")
    val historyTextFlow = _historyTextFlow


    fun getConversion(baseC: String, targetC: String, amountC: String) {
        getConversionUseCase(baseC, targetC, amountC).onEach { result ->
            when (result) {
                is Resource.Success -> _conversionResult.value = (Math.round(result.data!!.conversionResult * 100.0) / 100.0).toString()
                is Resource.Error -> _conversionResult.value = result.message.toString()
                is Resource.Loading -> _conversionResult.value = ""
            }
        }.launchIn(viewModelScope)
    }

    fun appendButtonChar(char: String) {
        when (char) {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "-" -> {
                if (_hasBeenEval.value) {
                    _mainTextFlow.value = char
                    _hasBeenEval.value = false
                } else {
                    _mainTextFlow.value = _mainTextFlow.value + char
                }

            }
            ".", "+", "*", "/" -> {
                if (_mainTextFlow.value.isNotEmpty() && _mainTextFlow.value.last().toString()
                    !in arrayOf("+", "-", "/", "*", ".")
                ) {
                    _mainTextFlow.value = _mainTextFlow.value + char
                    _hasBeenEval.value = false
                }
            }
            "Del" -> _mainTextFlow.value = _mainTextFlow.value.dropLast(1)
        }
    }

    fun calculateResult() {
        try {
            _historyTextFlow.value = _mainTextFlow.value
            _mainTextFlow.value = Expressions().eval(_mainTextFlow.value).toString()
            _hasBeenEval.value = true
        } catch (e: Exception) {
            _historyTextFlow.value = ""
        }
    }

    fun appendHistory() {
        try {
            _mainTextFlow.value =
                _mainTextFlow.value + Expressions().eval(_historyTextFlow.value).toString()
        } catch (e: Exception) {
            _historyTextFlow.value = ""
        }
    }

    fun clearTexts() {
        if (_hasBeenEval.value) {
            _hasBeenEval.value = false
        }
        _mainTextFlow.value = ""
        _historyTextFlow.value = ""
        _conversionResult.value = ""
    }


}