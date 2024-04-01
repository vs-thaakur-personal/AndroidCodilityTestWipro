package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Float,
    val images: List<String>,
    val discountPercentage: Float,
    val stock: Int,
    val category: String,
    val rating: Double,
)
