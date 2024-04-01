package com.example.data.retrofit

import com.example.data.models.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DashboardService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int,@Query("skip") skip: Int): ProductResponse?

    @GET("products/search")
    suspend fun searchProduct(@Query("q") query: String): ProductResponse?

    @GET("products/categories")
    suspend fun getAllCategories(): List<String>?


}