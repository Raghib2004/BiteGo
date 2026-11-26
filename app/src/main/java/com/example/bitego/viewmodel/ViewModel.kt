package com.example.bitego.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.bitego.data.FoodData
import com.example.bitego.data.FoodItem

class FoodViewModel : ViewModel() {

    val foodList = FoodData.foodList

    // Favorites
    val favorites = mutableStateListOf<FoodItem>()

    // Shopping Cart
    val cartItems = mutableStateMapOf<FoodItem, Int>()

    // Add or remove favorite
    fun toggleFavorite(food: FoodItem) {

        if (favorites.contains(food)) {
            favorites.remove(food)
        } else {
            favorites.add(food)
        }
    }

    // Check whether food is a favorite
    fun isFavorite(food: FoodItem): Boolean {
        return favorites.contains(food)
    }

    // Add food to cart
    fun addToCart(food: FoodItem) {

        val quantity = cartItems[food] ?: 0

        cartItems[food] = quantity + 1
    }

    // Decrease quantity
    fun decreaseQuantity(food: FoodItem) {

        val quantity = cartItems[food] ?: 0

        if (quantity > 1) {
            cartItems[food] = quantity - 1
        } else {
            cartItems.remove(food)
        }
    }

    // Remove food completely
    fun removeFromCart(food: FoodItem) {
        cartItems.remove(food)
    }

    // Calculate subtotal
    fun getSubtotal(): Int {

        return cartItems.entries.sumOf {
            it.key.price * it.value
        }
    }

    // Calculate delivery fee
    fun getDeliveryFee(): Int {
        return if (cartItems.isNotEmpty()) 40 else 0
    }

    // Calculate total
    fun getTotal(): Int {
        return getSubtotal() + getDeliveryFee()
    }

    // Clear cart
    fun clearCart() {
        cartItems.clear()
    }
}