package com.example.quickcart.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.quickcart.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        // Delay for 2 seconds and then check login state
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
            
            val intent = if (isLoggedIn) {
                Intent(this, com.example.quickcart.MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish() // Prevents user from going back to splash screen
        }, 2000)
    }
}
