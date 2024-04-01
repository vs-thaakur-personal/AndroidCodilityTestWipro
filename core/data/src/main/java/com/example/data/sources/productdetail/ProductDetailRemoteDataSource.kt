package com.example.data.sources.productdetail

import com.example.data.models.Product
import com.example.data.retrofit.ProductDetailService
import javax.inject.Inject

class ProductDetailRemoteDataSource @Inject constructor(private val apiService: ProductDetailService) :
    BaseProductDetailDataSource {
    override suspend fun getProductById(id: Int): Result<Product?> {
        return runCatching { apiService.getProductById(id) }
    }

}