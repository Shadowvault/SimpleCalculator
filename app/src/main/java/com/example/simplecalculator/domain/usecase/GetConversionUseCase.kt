package com.example.simplecalculator.domain.usecase

import com.example.simplecalculator.common.Resource
import com.example.simplecalculator.data.remote.dto.toConversionWithAmount
import com.example.simplecalculator.domain.ConversionRepository
import com.example.simplecalculator.domain.model.ConversionWithAmount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetConversionUseCase @Inject constructor(
    private val repository: ConversionRepository
){
    operator fun invoke(baseC: String, targetC: String, amountC: String) : Flow<Resource<ConversionWithAmount>> = flow {
        try {
            emit(Resource.Loading<ConversionWithAmount>())
            val conversion = repository.getConversion(baseC, targetC, amountC).toConversionWithAmount()
            emit(Resource.Success<ConversionWithAmount>(conversion))
        } catch(e: HttpException) {
            emit(Resource.Error<ConversionWithAmount>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error<ConversionWithAmount>(e.localizedMessage ?: "Couldn't reach server. Check your internet connection."))
        }
    }
}