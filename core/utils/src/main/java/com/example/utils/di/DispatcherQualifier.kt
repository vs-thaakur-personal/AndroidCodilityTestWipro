package com.example.utils.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherQualifier(val option: DispatcherOptions)

enum class DispatcherOptions {
    Default,
    IO,
}