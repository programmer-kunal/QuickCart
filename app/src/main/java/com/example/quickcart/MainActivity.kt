package com.example.quickcart

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcart.adapter.ProductAdapter
import com.example.quickcart.model.Product

class MainActivity : AppCompatActivity() {
    
    private lateinit var rvProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        loadDummyData()
    }

    private fun setupRecyclerView() {
        rvProducts = findViewById(R.id.rvProducts)
        // Using a GridLayout with 2 columns
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter(productList)
        rvProducts.adapter = productAdapter
    }

    private fun loadDummyData() {
        productList.apply {
            add(Product(1, "Fresh Apples", 2.99, R.drawable.img_apples, "Fruits"))
            add(Product(2, "Bananas (1 Dozen)", 1.49, R.drawable.img_bananas, "Fruits"))
            add(Product(3, "Organic Carrots", 0.99, R.drawable.img_vegetables, "Vegetables"))
            add(Product(4, "Broccoli Crown", 1.89, R.drawable.img_vegetables, "Vegetables"))
            add(Product(5, "Whole Milk 1L", 2.49, R.drawable.img_milk, "Dairy"))
            add(Product(6, "Cheddar Cheese", 4.99, R.drawable.img_milk, "Dairy"))
            add(Product(7, "Potato Chips", 3.49, R.drawable.img_snacks, "Snacks"))
            add(Product(8, "Mixed Nuts", 6.99, R.drawable.img_snacks, "Snacks"))
            add(Product(9, "Orange Juice", 3.99, R.drawable.ic_category_drinks, "Beverages"))
            add(Product(10, "Sparkling Water", 1.29, R.drawable.ic_category_drinks, "Beverages"))
        }
        productAdapter.notifyDataSetChanged()
    }
}