package com.example.lokaltask.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lokaltask.ApiResponse.Products
import com.example.lokaltask.R

class ProductAdapter(
    private val products: List<Products>,
    private val context: Context,
    private val listener: OnProductClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val productImageView : ImageView = itemView.findViewById(R.id.product_image)
        val categoryTextView : TextView = itemView.findViewById(R.id.categoryTextView)
        val discountTextView : TextView = itemView.findViewById(R.id.discountTextView)
        val priceTextView : TextView = itemView.findViewById(R.id.priceTextView)
//        val ratingBar : RatingBar = itemView.findViewById(R.id.ratingBar)
//        val stockAvailabilityTextView : TextView = itemView.findViewById(R.id.stockAvailabilityTextView)
        // Add other views you want to bind data to
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }




    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.titleTextView.text = product.title
        holder.categoryTextView.text = product.category
//        holder.descriptionTextView.text = product.description
//        holder.ratingBar.rating = product.rating.toFloat()
        holder.priceTextView.text = "Price : " + product.price.toString()
        holder.discountTextView.text= "(" + product.discountPercentage.toString() + "% off)"





//        Picasso.get().load(product.thumbnail).into(holder.productImageView)

        Glide.with(holder.productImageView.context)
            .load(product.thumbnail)
            .into(holder.productImageView)


        holder.itemView.setOnClickListener {
            listener.onProductClick(product)
        }

        Log.d("TAG", "onBindViewHolder: 1255==> ${product.title}")
//        Toast.makeText(context, "${product.title}", Toast.LENGTH_SHORT).show()
        // Bind other views in the item to the product data
    }

    interface OnProductClickListener {
        fun onProductClick(product: Products)
    }


}
