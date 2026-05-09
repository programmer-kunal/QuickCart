package com.example.quickcart.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickcart.R
import com.example.quickcart.utils.CartManager
import com.google.android.material.button.MaterialButton

class CheckoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        val tvCheckoutItemsCount = findViewById<TextView>(R.id.tvCheckoutItemsCount)
        val tvCheckoutSubtotal = findViewById<TextView>(R.id.tvCheckoutSubtotal)
        
        tvCheckoutItemsCount.text = "Items Total (${CartManager.getTotalItems()})"
        tvCheckoutSubtotal.text = String.format("$%.2f", CartManager.getSubtotal())

        findViewById<MaterialButton>(R.id.btnPlaceOrder).setOnClickListener {
            startActivity(Intent(this, OrderSuccessActivity::class.java))
        }
    }
}
