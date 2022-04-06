package com.example.simplecalculator.domain.model

import com.google.gson.annotations.SerializedName

data class ConversionWithAmount(
    val baseCode: String,
    val conversionRate: Double,
    val conversionResult: Double,
    val result: String,
    val targetCode: String,
)
