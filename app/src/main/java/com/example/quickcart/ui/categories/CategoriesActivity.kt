package com.example.quickcart.ui.categories

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickcart.R
import com.example.quickcart.utils.NavigationUtils

class CategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categories)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.btnBack).setOnClickListener { finish() }

        setupCategoryClick(R.id.cardFruits, "Fruits")
        setupCategoryClick(R.id.cardVegetables, "Vegetables")
        setupCategoryClick(R.id.cardDairy, "Dairy")
        setupCategoryClick(R.id.cardSnacks, "Snacks")
        setupCategoryClick(R.id.cardDrinks, "Drinks")
    }

    private fun setupCategoryClick(cardId: Int, categoryName: String) {
        findViewById<com.google.android.material.card.MaterialCardView>(cardId).setOnClickListener {
            val intent = android.content.Intent(this, com.example.quickcart.MainActivity::class.java)
            intent.putExtra("SELECTED_CATEGORY", categoryName)
            intent.flags = android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        NavigationUtils.setupBottomNavigation(this, R.id.navCategories)
    }
}
