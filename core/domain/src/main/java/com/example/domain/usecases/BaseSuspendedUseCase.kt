package com.example.domain.usecases

interface BaseSuspendedUseCase<in IN, out OUT> {
    suspend fun execute(input: IN): OUT
}