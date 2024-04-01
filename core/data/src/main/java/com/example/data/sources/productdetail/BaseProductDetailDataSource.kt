package com.example.data.sources.productdetail

import com.example.data.models.Product

interface BaseProductDetailDataSource {
    suspend fun getProductById(id: Int): Result<Product?>
}