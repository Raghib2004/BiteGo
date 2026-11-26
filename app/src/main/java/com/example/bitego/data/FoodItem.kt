package com.example.bitego.data

data class FoodItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val category: String,
    val emoji: String,
    val rating: Double
)