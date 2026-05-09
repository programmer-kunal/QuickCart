package com.example.quickcart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcart.R
import com.example.quickcart.model.Product

class ProductAdapter(private var productList: List<Product>) : 
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProductImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvProductPrice: TextView = itemView.findViewById(R.id.tvProductPrice)
        val btnAdd: Button = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        
        holder.ivProductImage.setImageResource(product.image)
        holder.tvProductName.text = product.name
        holder.tvProductPrice.text = String.format("$%.2f", product.price)
        
        holder.btnAdd.setOnClickListener {
            com.example.quickcart.utils.CartManager.addToCart(product)
            Toast.makeText(holder.itemView.context, "Added to cart", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnClickListener {
            val intent = android.content.Intent(holder.itemView.context, com.example.quickcart.ui.product.ProductDetailsActivity::class.java)
            intent.putExtra("PRODUCT_ID", product.id)
            intent.putExtra("PRODUCT_NAME", product.name)
            intent.putExtra("PRODUCT_PRICE", product.price)
            intent.putExtra("PRODUCT_IMAGE", product.image)
            intent.putExtra("PRODUCT_CATEGORY", product.category)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun updateData(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}
