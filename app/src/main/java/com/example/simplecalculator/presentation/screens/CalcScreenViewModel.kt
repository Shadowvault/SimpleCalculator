package com.example.simplecalculator.presentation.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplecalculator.common.Resource
import com.example.simplecalculator.domain.usecase.GetConversionUseCase
import com.github.keelar.exprk.Expressions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class CalcScreenViewModel @Inject constructor(
    private val getConversionUseCase: GetConversionUseCase,
) : ViewModel() {

    private val _conversionResult = mutableStateOf("")
    val conversion: State<String> = _conversionResult

    private val _hasBeenEval = mutableStateOf(false)

    private val _mainTextFlow = mutableStateOf("")
    val mainTextState = _mainTextFlow

    private val _historyTextFlow = mutableStateOf("")
    val historyTextFlow = _historyTextFlow

    private val _roundBracketCount = mutableStateOf(0)
    private val _canAppendDot = mutableStateOf(true)

    fun getConversion(baseC: String, targetC: String, amountC: String) {
        getConversionUseCase(baseC, targetC, amountC).onEach { result ->
            when (result) {
                is Resource.Success -> _conversionResult.value =
                    (round(result.data!!.conversionResult * 100.0) / 100.0).toString()
                is Resource.Error -> _conversionResult.value = result.message.toString()
                is Resource.Loading -> _conversionResult.value = ""
            }
        }.launchIn(viewModelScope)
    }

    fun appendButtonChar(char: String) {
        when (char) {
            "(" -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    if (_mainTextFlow.value.last().toString()
                        !in arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ")", ".")
                    ) {
                        _mainTextFlow.value += "("
                        _roundBracketCount.value += 1
                    }
                } else {
                    _mainTextFlow.value = "("
                    _roundBracketCount.value += 1
                }

            }
            ")" -> {
                if (_roundBracketCount.value > 0) {
                    if (_mainTextFlow.value.isNotEmpty()) {
                        if (_mainTextFlow.value.last().toString()
                            !in arrayOf(".", "-", "+", "*", "/", "(")
                        ) {
                            _mainTextFlow.value += ")"
                            _roundBracketCount.value -= 1
                        }
                    }
                }
            }
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    if (_mainTextFlow.value.last().toString() != ")") {
                        if (_hasBeenEval.value) {
                            _mainTextFlow.value = char
                            _hasBeenEval.value = false
                        } else {
                            _mainTextFlow.value += char
                        }
                    }
                } else {
                    if (_hasBeenEval.value) {
                        _mainTextFlow.value = char
                        _hasBeenEval.value = false
                    } else {
                        _mainTextFlow.value += char
                    }
                }
            }
            "-" -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    if (_mainTextFlow.value.last().toString() != ".") {
                        _mainTextFlow.value += char
                        _hasBeenEval.value = false
                        _canAppendDot.value = true
                    }
                }
            }
            "." -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    if (_mainTextFlow.value.last().toString()
                        !in arrayOf("+", "-", "/", "*", ".", "(", ")")
                    ) {
                        if (_canAppendDot.value) {
                            _mainTextFlow.value += char
                            _hasBeenEval.value = false
                            _canAppendDot.value = false
                        }
                    }
                }
            }
            "+", "*", "/" -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    if (_mainTextFlow.value.last().toString()
                        !in arrayOf("+", "-", "/", "*", ".", "(")
                    ) {
                        _mainTextFlow.value += char
                        _hasBeenEval.value = false
                        _canAppendDot.value = true
                    }
                }
            }
            "Del" -> {
                if (_mainTextFlow.value.isNotEmpty()) {
                    when (_mainTextFlow.value.last().toString()) {
                        "-", "+", "*", "/" -> _canAppendDot.value = false
                        "." -> _canAppendDot.value = true
                        "(" -> _roundBracketCount.value -= 1
                        ")" -> _roundBracketCount.value += 1
                    }
                    _mainTextFlow.value = _mainTextFlow.value.dropLast(1)
                }
            }
        }
    }

    fun calculateResult() {
        var tryEval = _mainTextFlow.value
        var countB = _roundBracketCount.value
        while (tryEval.isNotEmpty()) {
            if (tryEval.last().toString() in arrayOf("-", "+", "*", "/", ".", "(")) {
                if (tryEval.last().toString() == "(") {
                    countB -= 1
                }
                tryEval = tryEval.dropLast(1)
            } else {
                break
            }
        }
        if (countB > 0) {
            tryEval += ")".repeat(countB)
        }
        try {
            _historyTextFlow.value = tryEval
            tryEval = Expressions().eval(tryEval).toString()
            _mainTextFlow.value = tryEval
            _hasBeenEval.value = true
            _roundBracketCount.value = 0
            _canAppendDot.value =
                !_mainTextFlow.value.matches("^[+-]?([0-9]+\\.[0-9]*|\\.[0-9]+)\$".toRegex())

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
        _canAppendDot.value = true
        _roundBracketCount.value = 0
    }

}