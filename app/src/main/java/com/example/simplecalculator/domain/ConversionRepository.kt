package com.example.simplecalculator.domain

import com.example.simplecalculator.data.remote.dto.ConversionWithAmountDto

interface ConversionRepository {

    suspend fun getConversion(baseC: String, targetC: String, amountC: String) : ConversionWithAmountDto

}