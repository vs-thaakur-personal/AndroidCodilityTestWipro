package com.example.data.repositories

import com.example.data.models.response.ProductResponse
import com.example.data.sources.dashboard.BaseDashboardDataSource
import com.example.data.toApiException
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val remoteDataSource: BaseDashboardDataSource) {
    suspend fun searchProducts(query: String): Result<ProductResponse?> {
        return remoteDataSource.getProductByQuery(query)
            .recoverCatching { throw it.toApiException() }
    }

    suspend fun getAllProducts(pageNumber: Int):  Result<ProductResponse?>{
        return  remoteDataSource.getProducts(pageNumber)
            .recoverCatching{
            throw it.toApiException()
        }
    }

    suspend fun getAllCategories(): Result<List<String>?> {
        return  remoteDataSource.getAllCategories()
            .recoverCatching { throw it.toApiException() }
    }
}