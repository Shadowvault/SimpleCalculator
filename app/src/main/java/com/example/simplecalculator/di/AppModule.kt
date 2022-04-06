package com.example.simplecalculator.di

import com.example.simplecalculator.common.Constants.BASE_URL
import com.example.simplecalculator.common.DispatcherProvider
import com.example.simplecalculator.data.remote.ConversionApi
import com.example.simplecalculator.data.remote.ConversionRepositoryImpl
import com.example.simplecalculator.domain.ConversionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConversionApi() : ConversionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ConversionApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConversionRepository(api: ConversionApi) : ConversionRepository {
        return ConversionRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}