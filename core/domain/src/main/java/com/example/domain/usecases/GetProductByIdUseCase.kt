package com.example.domain.usecases

import com.example.data.models.Product
import com.example.data.repositories.ProductDetailRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductDetailRepository) :
    BaseSuspendedUseCase<Int, Result<Product?>> {
    override suspend fun execute(input: Int): Result<Product?> {
        return repository.getProductDetail(input)
    }
}