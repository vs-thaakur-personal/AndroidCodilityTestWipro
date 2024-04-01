package com.example.domain.usecases

import com.example.data.repositories.DashboardRepository
import javax.inject.Inject

class GetProductDetailsUseCase  @Inject constructor(private val repository: DashboardRepository) :
    BaseSuspendedUseCase<Unit, Result<List<String>?>> {
    override suspend fun execute(input: Unit): Result<List<String>?> {
        return repository.getAllCategories()
    }
}