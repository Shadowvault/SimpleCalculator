package com.example.simplecalculator.presentation.screens

import com.example.simplecalculator.domain.model.ConversionWithAmount

data class CalcScreenState(
    val isLoading: Boolean = false,
    val coin: ConversionWithAmount? = null,
    val error: String = ""
)
