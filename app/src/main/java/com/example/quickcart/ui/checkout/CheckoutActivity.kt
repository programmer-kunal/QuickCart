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
        val rgPayment = findViewById<android.widget.RadioGroup>(R.id.rgPayment)
        val btnPlaceOrder = findViewById<MaterialButton>(R.id.btnPlaceOrder)
        
        tvCheckoutItemsCount.text = "Items Total (${CartManager.getTotalItems()})"
        tvCheckoutSubtotal.text = String.format("$%.2f", CartManager.getSubtotal())

        btnPlaceOrder.setOnClickListener {
            if (rgPayment.checkedRadioButtonId == -1) {
                android.widget.Toast.makeText(this, "Please select a payment method", android.widget.Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prevent multiple clicks and show loading state
            btnPlaceOrder.isEnabled = false
            btnPlaceOrder.text = "Processing..."
            btnPlaceOrder.setBackgroundColor(android.graphics.Color.parseColor("#A0A0A0"))

            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, OrderSuccessActivity::class.java))
                finish()
            }, 1500)
        }
    }
}
