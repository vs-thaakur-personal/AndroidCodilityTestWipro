package com.example.domain.usecases

import com.example.data.models.response.ProductResponse
import com.example.data.repositories.DashboardRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: DashboardRepository) :
    BaseSuspendedUseCase<Int, Result<ProductResponse?>> {
    override suspend fun execute(input: Int): Result<ProductResponse?> {
        return repository.getAllProducts(input)
    }
}