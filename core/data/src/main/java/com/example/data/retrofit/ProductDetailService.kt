package com.example.data.retrofit

import com.example.data.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailService {

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product
}