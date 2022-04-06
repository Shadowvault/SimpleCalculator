package com.example.simplecalculator.data.remote

import com.example.simplecalculator.data.remote.dto.ConversionWithAmountDto
import com.example.simplecalculator.domain.ConversionRepository
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(
    private val api : ConversionApi
) : ConversionRepository {
    override suspend fun getConversion(
        baseC: String,
        targetC: String,
        amountC: String
    ): ConversionWithAmountDto {
        return api.getConversion(baseC, targetC, amountC)
    }

}