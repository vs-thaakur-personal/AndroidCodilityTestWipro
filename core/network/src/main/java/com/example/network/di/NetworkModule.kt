package com.example.network.di

import androidx.core.os.BuildCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().also {
//           val level =  if(BuildConfig.DEBUG) {
//               HttpLoggingInterceptor.Level.BODY
//            } else  {
//               HttpLoggingInterceptor.Level.NONE
//           }
//            it.setLevel(level)
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

}