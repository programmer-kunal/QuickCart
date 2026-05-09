package com.example.quickcart.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickcart.R
import com.example.quickcart.ui.login.LoginActivity
import com.example.quickcart.utils.NavigationUtils
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        val sharedPreferences = getSharedPreferences("UserPrefs", android.content.Context.MODE_PRIVATE)
        val savedPhone = sharedPreferences.getString("USER_PHONE", null)
        if (!savedPhone.isNullOrEmpty()) {
            findViewById<android.widget.TextView>(R.id.tvPhone).text = "+1 $savedPhone"
        }

        findViewById<MaterialButton>(R.id.btnLogout).setOnClickListener {
            sharedPreferences.edit()
                .remove("USER_PHONE")
                .remove("is_logged_in")
                .apply()
                
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        NavigationUtils.setupBottomNavigation(this, R.id.navProfile)
    }
}
