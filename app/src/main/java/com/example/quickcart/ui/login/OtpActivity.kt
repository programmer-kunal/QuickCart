package com.example.quickcart.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quickcart.MainActivity
import com.example.quickcart.R

class OtpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        
        val etOtp = findViewById<EditText>(R.id.etOtp)
        val btnVerifyOtp = findViewById<Button>(R.id.btnVerifyOtp)
        
        btnVerifyOtp.setOnClickListener {
            val otp = etOtp.text.toString().trim()
            
            // Validate fake OTP "1234"
            if (otp == "1234") {
                val mobileNumber = intent.getStringExtra("MOBILE_NUMBER") ?: ""
                val sharedPreferences = getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("USER_PHONE", mobileNumber).apply()

                // On successful verification navigate to MainActivity
                val intent = Intent(this, MainActivity::class.java)
                // Clear the back stack so user can't navigate back to login flow
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid OTP. Please enter 1234", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
