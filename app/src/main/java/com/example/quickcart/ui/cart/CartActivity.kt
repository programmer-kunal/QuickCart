package com.example.quickcart.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcart.R
import com.example.quickcart.adapter.CartAdapter
import com.example.quickcart.ui.checkout.CheckoutActivity
import com.example.quickcart.utils.CartManager
import com.google.android.material.button.MaterialButton

class CartActivity : AppCompatActivity() {

    private lateinit var rvCartItems: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private lateinit var tvItemsCount: TextView
    private lateinit var tvSubtotal: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var btnCheckout: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        tvItemsCount = findViewById(R.id.tvItemsCount)
        tvSubtotal = findViewById(R.id.tvSubtotal)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        btnCheckout = findViewById(R.id.btnCheckout)

        setupRecyclerView()
        updateBillSummary()

        btnCheckout.setOnClickListener {
            if (CartManager.cartItems.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
        }
    }

    private fun setupRecyclerView() {
        rvCartItems = findViewById(R.id.rvCartItems)
        rvCartItems.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartAdapter(CartManager.cartItems.toList()) {
            updateBillSummary()
        }
        rvCartItems.adapter = cartAdapter
    }

    private fun updateBillSummary() {
        val totalItems = CartManager.getTotalItems()
        tvItemsCount.text = "$totalItems items ready for delivery"

        val subtotal = CartManager.getSubtotal()
        tvSubtotal.text = String.format("$%.2f", subtotal)
        
        // Delivery fee is FREE as per UI design for simplicity
        tvTotalAmount.text = String.format("$%.2f", subtotal)
        
        // Update checkout button text to include total
        btnCheckout.text = String.format("Checkout $%.2f", subtotal)
    }
}
