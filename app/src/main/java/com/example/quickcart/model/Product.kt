package com.example.quickcart.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val image: Int, // Drawable resource ID
    val category: String
)
