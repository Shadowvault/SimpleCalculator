package com.example.simplecalculator.data.remote.dto


import com.example.simplecalculator.domain.model.ConversionWithAmount
import com.google.gson.annotations.SerializedName

data class ConversionWithAmountDto(
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("conversion_rate")
    val conversionRate: Double,
    @SerializedName("conversion_result")
    val conversionResult: Double,
    @SerializedName("documentation")
    val documentation: String,
    @SerializedName("result")
    val result: String,
    @SerializedName("target_code")
    val targetCode: String,
    @SerializedName("terms_of_use")
    val termsOfUse: String,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Int,
    @SerializedName("time_last_update_utc")
    val timeLastUpdateUtc: String,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Int,
    @SerializedName("time_next_update_utc")
    val timeNextUpdateUtc: String

)

fun ConversionWithAmountDto.toConversionWithAmount() : ConversionWithAmount {
    return ConversionWithAmount(
        baseCode = baseCode,
        targetCode = targetCode,
        result = result,
        conversionRate = conversionRate,
        conversionResult = conversionResult
    )
}