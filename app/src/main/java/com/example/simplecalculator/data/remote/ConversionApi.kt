package com.example.simplecalculator.data.remote

import com.example.simplecalculator.data.remote.dto.ConversionWithAmountDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ConversionApi {

    @GET("/pair/{baseC}/{targetC}/{amountC}/")
    suspend fun getConversion(
        @Path("baseC") baseC : String,
        @Path("targetC") targetC : String,
        @Path("amountC") amountC : String
    ): ConversionWithAmountDto

}