package com.example.quickcart.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.quickcart.MainActivity
import com.example.quickcart.R
import com.example.quickcart.ui.cart.CartActivity
import com.example.quickcart.ui.categories.CategoriesActivity
import com.example.quickcart.ui.profile.ProfileActivity

object NavigationUtils {

    fun setupBottomNavigation(activity: Activity, activeTabId: Int) {
        val navHome = activity.findViewById<LinearLayout>(R.id.navHome)
        val navCategories = activity.findViewById<LinearLayout>(R.id.navCategories)
        val navCart = activity.findViewById<LinearLayout>(R.id.navCart)
        val navProfile = activity.findViewById<LinearLayout>(R.id.navProfile)

        if (navHome == null || navCategories == null || navCart == null || navProfile == null) {
            return
        }

        // Set active state
        setActiveState(activity, R.id.ivNavHome, R.id.tvNavHome, activeTabId == R.id.navHome)
        setActiveState(activity, R.id.ivNavCategories, R.id.tvNavCategories, activeTabId == R.id.navCategories)
        setActiveState(activity, R.id.ivNavCart, R.id.tvNavCart, activeTabId == R.id.navCart)
        setActiveState(activity, R.id.ivNavProfile, R.id.tvNavProfile, activeTabId == R.id.navProfile)

        // Set click listeners
        navHome.setOnClickListener {
            if (activeTabId != R.id.navHome) {
                navigateTo(activity, MainActivity::class.java)
            }
        }

        navCategories.setOnClickListener {
            if (activeTabId != R.id.navCategories) {
                navigateTo(activity, CategoriesActivity::class.java)
            }
        }

        navCart.setOnClickListener {
            if (activeTabId != R.id.navCart) {
                navigateTo(activity, CartActivity::class.java)
            }
        }

        navProfile.setOnClickListener {
            if (activeTabId != R.id.navProfile) {
                navigateTo(activity, ProfileActivity::class.java)
            }
        }
    }

    private fun setActiveState(activity: Activity, iconId: Int, textId: Int, isActive: Boolean) {
        val icon = activity.findViewById<ImageView>(iconId)
        val text = activity.findViewById<TextView>(textId)
        
        val activeColor = Color.parseColor("#1AB64F")
        val inactiveColor = Color.parseColor("#6D7B6B")
        
        val color = if (isActive) activeColor else inactiveColor
        
        icon.setColorFilter(color)
        text.setTextColor(color)
    }

    private fun navigateTo(activity: Activity, targetClass: Class<*>) {
        val intent = Intent(activity, targetClass)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        activity.startActivity(intent)
        // Add a lightweight fade transition
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}
