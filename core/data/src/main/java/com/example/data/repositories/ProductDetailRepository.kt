package com.example.data.repositories

import com.example.data.models.Product
import com.example.data.sources.productdetail.BaseProductDetailDataSource
import com.example.data.toApiException
import javax.inject.Inject

class ProductDetailRepository @Inject constructor(private val productDetailDataSource: BaseProductDetailDataSource) {
    suspend fun getProductDetail(id: Int): Result<Product?> {
        return productDetailDataSource.getProductById(id).recoverCatching {
            throw it.toApiException()
        }
    }
}