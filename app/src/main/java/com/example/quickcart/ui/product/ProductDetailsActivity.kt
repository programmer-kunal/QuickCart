package com.example.quickcart.ui.product

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickcart.R
import com.example.quickcart.model.Product
import com.example.quickcart.utils.CartManager
import com.google.android.material.button.MaterialButton

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val productId = intent.getIntExtra("PRODUCT_ID", -1)
        val productName = intent.getStringExtra("PRODUCT_NAME") ?: ""
        val productPrice = intent.getDoubleExtra("PRODUCT_PRICE", 0.0)
        val productImage = intent.getIntExtra("PRODUCT_IMAGE", 0)
        val productCategory = intent.getStringExtra("PRODUCT_CATEGORY") ?: ""

        val ivProductImage = findViewById<ImageView>(R.id.ivProductImage)
        val tvProductName = findViewById<TextView>(R.id.tvProductName)
        val tvProductCategory = findViewById<TextView>(R.id.tvProductCategory)
        val tvProductPrice = findViewById<TextView>(R.id.tvProductPrice)
        val btnAddToCart = findViewById<MaterialButton>(R.id.btnAddToCart)

        ivProductImage.setImageResource(productImage)
        tvProductName.text = productName
        tvProductCategory.text = productCategory
        tvProductPrice.text = String.format("$%.2f", productPrice)

        btnAddToCart.setOnClickListener {
            if (productId != -1) {
                val product = Product(productId, productName, productPrice, productImage, productCategory)
                CartManager.addToCart(product)
                Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
