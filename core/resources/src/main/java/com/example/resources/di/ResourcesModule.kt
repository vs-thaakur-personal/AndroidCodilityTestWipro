package com.example.resources.di

import android.content.Context
import com.example.resources.ValidationManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ResourcesModule {
    fun provideValidationManager(@ApplicationContext context: Context): ValidationManager {
        return ValidationManager(context)
    }
}