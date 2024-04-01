package com.example.data.models.response

import com.example.data.models.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(val products: List<Product>, val total: Int, val skip: Int, val limit: Int)