package com.example.lokaltask;

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.lokaltask.ApiResponse.Products
import com.example.lokaltask.adapter.ImagePagerAdapter

class ProductDetailsActivity : AppCompatActivity() {
    private var imageList: List<String> = ArrayList() // Initialize this with your list of image URLs
    private lateinit var viewPager: ViewPager2
    private val handler = Handler()
    private var imagePosition = 0
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val stockAvailabilityTextView : TextView = findViewById(R.id.stockAvailabilityTextView)

        val product = intent.getParcelableExtra<Products>("product")



        Log.d(TAG, "onCreate: $product")

        if (product != null) {
            setTitle(product.title)

            // Now you can use the product data to populate your UI
            imageList = product!!.images

//        val productImage = findViewById<ImageView>(R.id.product_image)
            val titleTextView = findViewById<TextView>(R.id.titleTextView)
            val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
            val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
            val priceTextView = findViewById<TextView>(R.id.priceTextView)
            val discountTextView = findViewById<TextView>(R.id.discountTextView)
            titleTextView.text = product.title
            descriptionTextView.text = product.description
            ratingBar.rating = product.rating.toFloat()

            priceTextView.text = product.price.toString()
            discountTextView.text = "(" +product.discountPercentage.toString()  + "%  off)"
//            discountTextView.setTextColor(R.color.colorGreen)
//            discountTextView.apply {
//                setTextColor(R.color.colorGreen)
//            }

            if (product.stock > 0) {
                stockAvailabilityTextView.apply {
                    text = "In Stock"
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen)) // Use your desired color resource
                }
            } else {
                stockAvailabilityTextView.apply {
                    text = "Out of Stock"
                    setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed)) // Use your desired color resource
                }
            }

            viewPager = findViewById(R.id.imageViewPager)
            val imageAdapter = ImagePagerAdapter(imageList)
            viewPager.adapter = imageAdapter

            // Implement auto-scrolling
            handler.postDelayed(object : Runnable {
                override fun run() {
                    viewPager.setCurrentItem(imagePosition, true)
                    imagePosition = (imagePosition + 1) % imageList.size
                    handler.postDelayed(this, 2000) // Auto-scroll interval (2 seconds)
                }
            }, 2000) // Initial delay before auto-scroll starts
        }
        }



        // Populate the layout with product details


        // Load product image using a library like Picasso or Glide
        // Picasso.get().load(product.thumbnail).into(productImage)




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem = menu?.findItem(R.id.action_search)
searchItem?.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }



    companion object{
        private val TAG = "ProductDetailActivity"
    }
}
