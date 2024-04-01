package com.example.productdetails.di

import com.example.data.retrofit.ProductDetailService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProductDetailModule {
    private val json = Json { ignoreUnknownKeys = true }

    @Provides
    fun provideProductDetailService(client: OkHttpClient) : ProductDetailService {
        val baseUrl = "https://dummyjson.com/"
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(ProductDetailService::class.java)
    }
}