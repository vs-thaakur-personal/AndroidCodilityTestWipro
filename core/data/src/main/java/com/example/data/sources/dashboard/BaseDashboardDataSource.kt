package com.example.data.sources.dashboard

import com.example.data.models.response.ProductResponse

interface BaseDashboardDataSource {
    suspend fun getProductByQuery(query: String): Result<ProductResponse?>
    suspend fun getProducts(pageNumber: Int): Result<ProductResponse?>

    suspend fun getAllCategories(): Result<List<String>?>
}