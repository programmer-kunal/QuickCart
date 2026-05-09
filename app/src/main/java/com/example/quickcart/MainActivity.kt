package com.example.quickcart

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcart.adapter.ProductAdapter
import com.example.quickcart.model.Product
import com.example.quickcart.utils.NavigationUtils
import java.util.Locale

class MainActivity : AppCompatActivity() {
    
    private lateinit var rvProducts: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val allProducts = mutableListOf<Product>()
    private val displayedProducts = mutableListOf<Product>()

    private var currentQuery = ""
    private var currentCategory = "All"

    private lateinit var etSearch: EditText
    
    // Category Views
    private lateinit var llCatAll: LinearLayout
    private lateinit var llCatFruits: LinearLayout
    private lateinit var llCatVeg: LinearLayout
    private lateinit var llCatDairy: LinearLayout
    private lateinit var llCatSnacks: LinearLayout
    private lateinit var llCatDrinks: LinearLayout

    override fun onResume() {
        super.onResume()
        com.example.quickcart.utils.NavigationUtils.setupBottomNavigation(this, com.example.quickcart.R.id.navHome)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        loadDummyData()
        setupListeners()
        handleCategoryIntent(intent)
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleCategoryIntent(intent)
    }

    private fun handleCategoryIntent(intent: android.content.Intent?) {
        val category = intent?.getStringExtra("SELECTED_CATEGORY")
        if (category != null) {
            when (category) {
                "Fruits" -> selectCategory("Fruits", llCatFruits)
                "Vegetables" -> selectCategory("Vegetables", llCatVeg)
                "Dairy" -> selectCategory("Dairy", llCatDairy)
                "Snacks" -> selectCategory("Snacks", llCatSnacks)
                "Drinks" -> selectCategory("Drinks", llCatDrinks)
                else -> selectCategory("All", llCatAll)
            }
        }
    }

    private fun initViews() {
        etSearch = findViewById(R.id.etSearch)
        llCatAll = findViewById(R.id.llCatAll)
        llCatFruits = findViewById(R.id.llCatFruits)
        llCatVeg = findViewById(R.id.llCatVeg)
        llCatDairy = findViewById(R.id.llCatDairy)
        llCatSnacks = findViewById(R.id.llCatSnacks)
        llCatDrinks = findViewById(R.id.llCatDrinks)
    }

    private fun setupRecyclerView() {
        rvProducts = findViewById(R.id.rvProducts)
        rvProducts.setHasFixedSize(true)
        rvProducts.layoutManager = GridLayoutManager(this, 2)
        productAdapter = ProductAdapter(displayedProducts)
        rvProducts.adapter = productAdapter
    }

    private fun loadDummyData() {
        allProducts.apply {
            add(Product(1, "Fresh Apples", 2.99, R.drawable.img_apples, "Fruits"))
            add(Product(2, "Bananas (1 Dozen)", 1.49, R.drawable.img_bananas, "Fruits"))
            add(Product(3, "Organic Carrots", 0.99, R.drawable.img_vegetables, "Vegetables"))
            add(Product(4, "Broccoli Crown", 1.89, R.drawable.img_vegetables, "Vegetables"))
            add(Product(5, "Whole Milk 1L", 2.49, R.drawable.img_milk, "Dairy"))
            add(Product(6, "Cheddar Cheese", 4.99, R.drawable.img_milk, "Dairy"))
            add(Product(7, "Potato Chips", 3.49, R.drawable.img_snacks, "Snacks"))
            add(Product(8, "Mixed Nuts", 6.99, R.drawable.img_snacks, "Snacks"))
            add(Product(9, "Orange Juice", 3.99, R.drawable.ic_category_drinks, "Drinks"))
            add(Product(10, "Sparkling Water", 1.29, R.drawable.ic_category_drinks, "Drinks"))
        }
        displayedProducts.addAll(allProducts)
        productAdapter.notifyDataSetChanged()
    }

    private fun setupListeners() {
        // Search Listener
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentQuery = s.toString().trim()
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Category Listeners
        llCatAll.setOnClickListener { selectCategory("All", llCatAll) }
        llCatFruits.setOnClickListener { selectCategory("Fruits", llCatFruits) }
        llCatVeg.setOnClickListener { selectCategory("Vegetables", llCatVeg) }
        llCatDairy.setOnClickListener { selectCategory("Dairy", llCatDairy) }
        llCatSnacks.setOnClickListener { selectCategory("Snacks", llCatSnacks) }
        llCatDrinks.setOnClickListener { selectCategory("Drinks", llCatDrinks) }
    }

    private fun selectCategory(category: String, selectedLayout: LinearLayout) {
        currentCategory = category
        
        // Reset all category styles
        resetCategoryStyles()
        
        // Apply active style to selected category
        val imageView = selectedLayout.getChildAt(0) as ImageView
        val textView = selectedLayout.getChildAt(1) as TextView
        
        imageView.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1AB64F"))
        imageView.imageTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FFFFFF"))
        textView.setTextColor(android.graphics.Color.parseColor("#1AB64F"))
        textView.setTypeface(null, android.graphics.Typeface.BOLD)

        applyFilters()
    }

    private fun resetCategoryStyles() {
        val categories = listOf(llCatAll, llCatFruits, llCatVeg, llCatDairy, llCatSnacks, llCatDrinks)
        for (layout in categories) {
            val imageView = layout.getChildAt(0) as ImageView
            val textView = layout.getChildAt(1) as TextView
            
            imageView.backgroundTintList = null
            imageView.imageTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#006E2B"))
            textView.setTextColor(android.graphics.Color.parseColor("#161D16"))
            textView.setTypeface(null, android.graphics.Typeface.NORMAL)
        }
    }

    private fun applyFilters() {
        val filteredList = allProducts.filter { product ->
            val matchesCategory = if (currentCategory == "All") true else product.category == currentCategory
            val matchesQuery = if (currentQuery.isEmpty()) true else product.name.lowercase(Locale.getDefault()).contains(currentQuery.lowercase(Locale.getDefault()))
            matchesCategory && matchesQuery
        }
        productAdapter.updateData(filteredList)
    }
}