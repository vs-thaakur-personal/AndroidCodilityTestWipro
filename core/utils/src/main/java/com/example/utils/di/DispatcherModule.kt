package com.example.utils.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @DispatcherQualifier(DispatcherOptions.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatcherQualifier(DispatcherOptions.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}