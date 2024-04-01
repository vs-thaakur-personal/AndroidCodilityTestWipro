package com.example.data.sources.dashboard

import com.example.data.models.response.ProductResponse
import com.example.data.retrofit.DashboardService
import javax.inject.Inject

class DashboardRemoteDataSource @Inject constructor(private val apiService: DashboardService): BaseDashboardDataSource {

    override suspend fun getProducts(pageNumber: Int): Result<ProductResponse?> {
        return runCatching {  apiService.getProducts(limit = 10, skip = pageNumber*10)}
    }

    override suspend fun getAllCategories(): Result<List<String>?> {
        return  runCatching { apiService.getAllCategories() }
    }

    override suspend fun getProductByQuery(query: String): Result<ProductResponse?> {
        return runCatching {  apiService.searchProduct(query)}
    }
}