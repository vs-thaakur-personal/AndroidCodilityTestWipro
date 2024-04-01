package com.example.domain.usecases

import com.example.data.models.response.ProductResponse
import com.example.data.repositories.DashboardRepository
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(private val repository: DashboardRepository) :
    BaseSuspendedUseCase<String, Result<ProductResponse?>> {
    override suspend fun execute(input: String): Result<ProductResponse?> {
        return repository.searchProducts(input)
    }
}