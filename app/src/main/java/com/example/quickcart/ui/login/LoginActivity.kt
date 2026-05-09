package com.example.quickcart.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickcart.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        val etMobileNumber = findViewById<EditText>(R.id.etMobileNumber)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        
        btnLogin.setOnClickListener {
            val mobileNumber = etMobileNumber.text.toString().trim()
            
            // Simple validation: Ensure number is not empty and has 10 digits
            if (mobileNumber.isNotEmpty() && mobileNumber.length == 10) {
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("MOBILE_NUMBER", mobileNumber)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid 10-digit mobile number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
