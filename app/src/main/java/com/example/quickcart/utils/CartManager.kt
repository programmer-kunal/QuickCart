package com.example.quickcart.utils

import com.example.quickcart.model.CartItem
import com.example.quickcart.model.Product

object CartManager {
    val cartItems = mutableListOf<CartItem>()

    fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product, 1))
        }
    }

    fun removeFromCart(productId: Int) {
        cartItems.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
        } else {
            cartItems.find { it.product.id == productId }?.quantity = quantity
        }
    }

    fun getSubtotal(): Double {
        return cartItems.sumOf { it.product.price * it.quantity }
    }

    fun getTotalItems(): Int {
        return cartItems.sumOf { it.quantity }
    }

    fun clearCart() {
        cartItems.clear()
    }
}
