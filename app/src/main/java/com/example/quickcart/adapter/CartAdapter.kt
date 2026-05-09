package com.example.quickcart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quickcart.R
import com.example.quickcart.model.CartItem
import com.example.quickcart.utils.CartManager

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onCartUpdated: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCartProductImage: ImageView = itemView.findViewById(R.id.ivCartProductImage)
        val tvCartProductName: TextView = itemView.findViewById(R.id.tvCartProductName)
        val tvCartProductCategory: TextView = itemView.findViewById(R.id.tvCartProductCategory)
        val tvCartProductPrice: TextView = itemView.findViewById(R.id.tvCartProductPrice)
        val tvCartQuantity: TextView = itemView.findViewById(R.id.tvCartQuantity)
        val btnCartPlus: ImageView = itemView.findViewById(R.id.btnCartPlus)
        val btnCartMinus: ImageView = itemView.findViewById(R.id.btnCartMinus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]
        
        holder.ivCartProductImage.setImageResource(item.product.image)
        holder.tvCartProductName.text = item.product.name
        holder.tvCartProductCategory.text = item.product.category
        holder.tvCartProductPrice.text = String.format("$%.2f", item.product.price)
        holder.tvCartQuantity.text = item.quantity.toString()

        holder.btnCartPlus.setOnClickListener {
            CartManager.updateQuantity(item.product.id, item.quantity + 1)
            notifyItemChanged(holder.bindingAdapterPosition)
            onCartUpdated()
        }

        holder.btnCartMinus.setOnClickListener {
            if (item.quantity <= 1) {
                CartManager.removeFromCart(item.product.id)
                val currentPos = holder.bindingAdapterPosition
                if (currentPos != RecyclerView.NO_POSITION) {
                    val updatedList = CartManager.cartItems.toList()
                    cartItems = updatedList
                    notifyItemRemoved(currentPos)
                    notifyItemRangeChanged(currentPos, updatedList.size)
                }
                android.widget.Toast.makeText(holder.itemView.context, "Item removed from cart", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                CartManager.updateQuantity(item.product.id, item.quantity - 1)
                notifyItemChanged(holder.bindingAdapterPosition)
            }
            onCartUpdated()
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun updateData(newCartItems: List<CartItem>) {
        this.cartItems = newCartItems
        notifyDataSetChanged()
    }
}
